#!/usr/bin/env bash
service postgresql start
service nginx start
if [ "$1" == "test" ]
then
   cd /WAECM && mvn clean test
   test_exit_code=$?
   echo "Unit Test Report"
   cat app/target/surefire-reports/*.txt
   exit "$test_exit_code"
elif [ "$1" == "build" ]
then
   cd /WAECM && mvn clean package &&
       cp /WAECM/app/target/app.jar /jar &&
       cp /WAECM/oauth/target/oauth.jar /jar
   tail -f /dev/null
elif [ "$1" == "deploy" ]
then
   cd /WAECM && mvn clean package &&
       cp /WAECM/app/target/app.jar /jar &&
       cp /WAECM/oauth/target/oauth.jar /jar
   java -jar /jar/app.jar &
       java -jar /jar/oauth.jar
fi