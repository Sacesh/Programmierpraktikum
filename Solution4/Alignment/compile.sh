#!/bin/bash
mvn package
mv target/*-jar-with-dependencies.jar alignment.jar
