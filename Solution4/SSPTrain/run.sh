#!/bin/sh
#mvn package
#sleep 1s
#mv target/*-jar-with-dependencies.jar ssptrain.jar
java -jar train.jar --db test.db --method GOR1 --model model1.txt
java -jar train.jar --db test.db --method GOR3 --model model3.txt
java -jar train.jar --db test.db --method GOR4 --model model4.txt
