#!/bin/sh
mvn package
sleep 1s
mv target/*-jar-with-dependencies.jar ssppredict.jar

java -jar ssppredict.jar --probabilities --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1hp
java -jar ssppredict.jar --probabilities --format HTML --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3hp
java -jar ssppredict.jar --probabilities --format HTML --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4hp

java -jar ssppredict.jar --probabilities --format TXT --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tp
java -jar ssppredict.jar --probabilities --format TXT --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3tp
java -jar ssppredict.jar --probabilities --format TXT --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4tp

java -jar ssppredict.jar --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1hn
java -jar ssppredict.jar --format HTML --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3hn
java -jar ssppredict.jar --format HTML --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4hn

java -jar ssppredict.jar --format TXT --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict > out1tn
java -jar ssppredict.jar --format TXT --model src/test/resources/Ugor3CB513DSSP.db.txt --seq predict > out3tn
java -jar ssppredict.jar --format TXT --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict > out4tn

# GOR 5
java -jar ssppredict.jar --format html --probabilities --model src/test/resources/Ugor1CB513DSSP.db.txt --aln src/test/resources/1chkb.aln > out5hp


