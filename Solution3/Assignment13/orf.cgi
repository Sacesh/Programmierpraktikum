#!/usr/bin/perl
use strict;
use File::Temp qw/ tempfile tempdir /;
use CGI::Carp qw(fatalsToBrowser);
use DBI;
use File::Basename;
use File::Copy;
use HTML::Entities;
use CGI qw(:standard);
#Create a temporary dir
my $tempdir = tempdir();
carp "Tempdir: $tempdir";
print header('text/html');
#my $tempdir = "/tmp/ulix";
#`mkdir -p $tempdir`;
#We do generate HTML
#Get the filename
my $dbTempFilename = param("sequence");
#Save the database query to the temporary file
my $dbFilename = $tempdir."/".basename($dbTempFilename);
my $dbBasename = basename($dbTempFilename);
carp "Writing to temp dir $tempdir \n";
#Copy the temp file to the database filename
copy($dbTempFilename,$dbFilename) or die "Copy failed: $!";
#Execute the ORF script
my $bindir = "/home/k/koehleru/Programmierpraktikum/Solution3/Assignment3/";
#orf_finder needs gnuplot script
`cp $bindir/gnuplot $tempdir/`;
my $output = `bash -c 'cd $tempdir && $bindir/orf_finder \"$dbBasename\"'`;
#Copy the graphics to a local dir in order to serve it)
#We deliberately chose bash syntax here because it's
#   1) shorter --> DRY
#   2) more portable, e.g. to shell scripts
#   3) language-agnostic
my $histoDirName = "histograms/".(time())."/";
mkdir($histoDirName);
`bash -c 'mv $tempdir/Res*/*.png $histoDirName'`;
#Rename them to 1.png, 2.pn g
#Find the sequence IDs of the forward and reverse sequences
my $fwdId = undef;
my $revId = undef;
if($output =~ m/Forward sequence ID:\s*(\d+)/) {
	$fwdId = $1;
} else {
	die "Can't find fwdId in output $output\n";
}
if($output =~ m/Reverse sequence ID:\s*(\d+)/) {
	$revId = $1;
} else {
	die "Can't find revId in output $output\n";
}
#Setup db conn
my $db = DBI->connect('DBI:mysql:bioprakt4;host=mysql2-ext.bio.ifi.lmu.de', 'bioprakt4', 'vGI5GCMg0x') || die "Could not connect to database: $DBI::errstr";	
#Find fwd ORFs
my $fwdQuery = $db->prepare("SELECT Orf.Start, Orf.Stop, Orf.Strand FROM Orf WHERE Orf.SeqId = ?");
my $revQuery = $db->prepare("SELECT Orf.Start, Orf.Stop, Orf.Strand FROM Orf WHERE Orf.SeqId = ?");
$fwdQuery->execute($fwdId);
$revQuery->execute($revId);
#Print the HTML prototype
print <<"EOHTML"
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Propra G4</title>
<style type="text/css" media="screen">
body { background: #e7e7e7; font-family: Verdana, sans-serif; font-size: 11pt; }
#page { background: #ffffff; margin: 50px; border: 2px solid #c0c0c0; padding: 10px; }
#header { background: #4b6983; border: 2px solid #7590ae; text-align: center; padding: 10px; color: #ffffff; }
#header h1 { color: #ffffff; }
#body { padding: 10px; }
span.tt { font-family: monospace; }
span.bold { font-weight: bold; }
a:link { text-decoration: none; font-weight: bold; color: #C00; background: #ffc; }
a:visited { text-decoration: none; font-weight: bold; color: #999; background: #ffc; }
a:active { text-decoration: none; font-weight: bold; color: #F00; background: #FC0; }
a:hover { text-decoration: none; color: #C00; background: #FC0; }
</style>
</head>
<body>
<div id="page">
 <div id="header">
 <h1>ORF Finder</h1>
 </div>
 <div id="body">
  <h2>orf_finder Output:</2>
  <pre style="font-weight:normal;">
  $output
  </pre>
  <h2>Histogram</h2>
EOHTML
;
#Show the histograms
opendir my($dh), $histoDirName or die "Couldn't open dir '$histoDirName': $!";
my @histoFiles = readdir $dh;
closedir $dh;

foreach my $histoFile (@histoFiles) {
	if($histoFile eq "." or $histoFile eq "..") {
		next;
	}
	print "<img src=\"$histoDirName$histoFile\" width=\"800\"></img><br/>\n";
}

print <<"EOHTML"
  <h2>Statistics</h2>
  <h2>ORFs</h2>
  <table border="1" style="font-weight:normal;"><tr><td><b>Strand</b></td><td><b>Start</b></td><td><b>Stop</b></td></tr>
EOHTML
;
my $result = undef;
while ($result = $fwdQuery->fetchrow_hashref() ) {
        my $start = $result->{Start};
        my $stop = $result->{Stop};
        my $strand = $result->{Strand};
        print "<tr><td>$strand</td><td>$start</td><td>$stop</td></tr>";
}
while ($result = $revQuery->fetchrow_hashref() ) {
        my $start = $result->{Start};
        my $stop = $result->{Stop};
        my $strand = $result->{Strand};
        print "<tr><td>$strand</td><td>$start</td><td>$stop</td></tr>";
}
print <<"EOHTML"
</table>
</body>
</html>
EOHTML
;

#Remove the temporary files
#`rm -rf $tempdir`;
$db->disconnect();
