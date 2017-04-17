#!/bin/bash
JBPM_CONTAINER=$1
if [ -z "$JBPM_CONTAINER" ]; then
   JBPM_CONTAINER='jbpm-container'
fi   
echo $JBPM_CONTAINER
JBPM_IP=$(docker inspect -f '{{ .NetworkSettings.IPAddress }}' $JBPM_CONTAINER)
wd=$PWD
echo $wd
cd src/main/resources/
sed -i  -e '/ip.address=/s/= .*/= '$JBPM_IP':8080/' application.properties
for i in {1..5};
do
	echo -n "."
	sleep 1
done
echo "**************workflow-ui war file creation*****************"
cd $wd
mvn clean install
