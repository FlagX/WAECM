#!/usr/bin/env bash
service postgresql start
service nginx start
if [ "$1" == "test" ]
then
   cd /WAECM && mvn clean verify
   test_exit_code=$?
   echo ""
   echo "Unit Test Report"
   cat app/target/surefire-reports/*.txt
   echo ""
   echo "Code Coverage"
   cat app/target/site/jacoco/jacoco.csv | column -s, -t
   exit "$test_exit_code"
elif [ "$1" == "build" ]
then
   cd /WAECM && mvn clean verify &&
       cp /WAECM/app/target/app.jar /jar &&
       cp /WAECM/oauth/target/oauth.jar /jar
   tail -f /dev/null
elif [ "$1" == "deploy" ]
then
   cd /WAECM && mvn clean install &&
       cp /WAECM/app/target/app.jar /jar &&
       cp /WAECM/oauth/target/oauth.jar /jar
   java -jar /jar/app.jar &
       java -jar /jar/oauth.jar
fi