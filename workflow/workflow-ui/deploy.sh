#!/bin/bash
TOMCAT_CONTAINER=$1
if [ -z "$TOMCAT_CONTAINER" ]; then
    TOMCAT_CONTAINER='tomcat-container'
fi
echo $TOMCAT_CONTAINER 
docker cp target/workflow-ui.war $TOMCAT_CONTAINER:/usr/local/tomcat/webapps
