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
my $pdbid= param("pdbid");
die "No pdbid given!\n" unless $pdbid;
my $contactDistance= param("contactdistance");
die "No contactdistance given!\n" unless $contactDistance;
my $atomType= param("atomtype");
die "No atomtype given!\n" unless $atomType;
my $localContactSequenceDist= param("localcontactsequencedistance");
die "No localcontactsequencedistance given!\n" unless $localContactSequenceDist;
#Execute the ORF script
my $bindir = "/home/k/koehleru/Programmierpraktikum/Solution3/Assignment12";
#orf_finder needs gnuplot script
my $imageFilename = "images/".(time());
my $output = `$bindir/makesscc -p $bindir -o $imageFilename -f $pdbid -d $contactDistance -t $atomType -l $localContactSequenceDist`;
#Find the sequence IDs of the forward and reverse sequences
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
 <h1>makesscc</h1>
 </div>
 <div id="body">
  <h2>makesscc Output for PDB $pdbid:</h2>
  <img src="$imageFilename.png" alt="This protein does not support this operation" width="400"></img>
  <pre style="font-weight:normal;">
  $output
  </pre>
</body>
</html>
EOHTML
;
#Delete temp files
#`rm $pdbid.sscc DistanceMatrix_$pdbid ContactMatrix_$pdbid`;
