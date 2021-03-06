#!/usr/bin/perl -w
#Insert a sequence by using a POST multipart/form-encoded file (not a string)
#Initialize CGI parser
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use DBI;

sub trim {		# trim a string
  my $string = $_[0];
  $string =~ s/^\s+//;
  $string =~ s/\s+$//;
  return $string;
}
#print header("application/json");
my $datafile = param("seqfile");
my $seqType = param("sequenceType");
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";
my $insertStmt = $db->prepare("INSERT INTO Seq (`Name`, `Seq`, `Type`, `OrganismId`) SELECT ?,?,?,Id from Organism WHERE Organism.Name = 'Unknown'");
#Even if the real type is RNA, we currently use DNA
my $originalSeqType = $seqType;
$seqType = "DNA" if $seqType eq "Nucleotide";
#Create the name of the sequence (unique)
my $name = "user-" . time();
#Read the data
my $processedData = "";
binmode $datafile;
my $data = "";
while(read $datafile,$inputdata,1024) {
  $data = $data.$inputdata;
}
#Remove
my $fastaHeader = "";
for (split /^/, $data) {
	chomp $_;
	if($_ !~ m/^>/) {
	    $processedData = $processedData.trim($_);
	} else {
	    $fastaHeader = substr($_, 1, length($_)-1);
	}
	chomp $processedData;
}
if(length($fastaHeader) > 40) {
  $fastaHeader = substr($fastaHeader, 0, 40) . "...";
}
#Write it to the DB
$insertStmt->execute($name, $processedData, $seqType,);
carp "Inserted user sequence $name into database, ID  $insertStmt->{mysql_insertid}\n";
#Write header
#print "{\"success\":true,\"name\":$name}";
#Write header
my $sequenceName = trim("[FASTA Upload] $fastaHeader");
print header();
print <<"EOHTML"
<html>
<head>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/jstorage.js"></script>
    <script type="text/javascript" src="../ws.js"></script>
    <script type="text/javascript" src="../js/jquery-ui.js"></script>
    <script type="text/javascript">
	addSequence(\"mysql:$name\", \"$sequenceName\", \"$originalSeqType\");
	window.history.back(-1);
    </script>
</head>
<body>
<h2>
</body>
</html>
EOHTML
;
$db->disconnect();
