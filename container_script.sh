exec /usr/bin/mongod &
if [ "$1" == "build" ]
then
   cd /WAECM && mvn clean package && cp /WAECM/target/group6-0.0.1-SNAPSHOT.jar /jar
   tail -f /dev/null
elif [ "$1" == "deploy" ]
then
   cd /WAECM && mvn clean package && cp /WAECM/target/group6-0.0.1-SNAPSHOT.jar /jar && java -jar /jar/group6-0.0.1-SNAPSHOT.jar
fi