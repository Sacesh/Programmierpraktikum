#! /usr/bin/perl -w

# Assignment:
#   The first step in secondary structure prediction is of course to acquire secondary structures from different sources. The local data directories provide both <pdb> and 
#   <dssp> files of the Protein Data Bank (pdb). Search and read the respective format specification and implement a perl tool which extracts the secondary structure from 
#   either file type (based on -t <mode>). A list of ids must be converted into a <dssp-file>.
#   Note that most proteins consist of multiple chains. Extract the structure of each chain separately in such cases. If an id does not exist locally download it from the web 
#   and preprocess it via the DSSP command line tool automatically.
# Syntax:
#   perl extract-dssp.pl
#   [--pdb <pdb-path>]           the path to the local pdb, overwrite default
#   [--dssp <dssp-path>]         the path to the local dssp, overwrite default
#   [--dssp-bin <dssp-binary>]   the path to the dssp binaries
#   -t <pdb|dssp|cmp>            'pdb' - read pdb files from <file-protein-list> 'dssp' - read dssp files from <file-protein-list> 'cmp' - compare each pdb and dssp for <file-
#                                protein-list> If "-t cmp" is given your STDERR should inform about inconsistent ids.
#   <file-protein-list>          is a newline separated list of pdb-ids
#   <dssp-file>
#   <file-protein-list> 

# 

# includes
use strict;
use LWP::Simple;
use Getopt::Std;

# read arguments
my %options=();
getopts("-pdb:-dssp:-dssp-bin:t:",\%options);

# arguments to vars
my $pdb     = "";
my $dssp    = "";
my $dsspBin = "";

print "";
