#!/bin/bash

# Set host ip
if [[ "$OSTYPE" == "darwin"* ]]; then
  hostIp=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}')
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
  hostIp=$(hostname -I | awk -v OFS=' ' '{print $1}')
elif [[ "$OSTYPE" == "msys"* ]] || [[ "$OSTYPE" == "cygwin"* ]] || [[ "$OSTYPE" == "wsl2"* ]]; then
  hostIp=$(ipconfig | grep "IPv4 Address" | awk 'NR==1{print $14}')
else
  echo "ERROR: cannot find host ip - setting to localhost"
  hostIp="localhost"
fi
#
#hostIp="localhost"
#echo $hostIp

# get version from build.gradle and project name from settings.gradle
version=$(grep "^version" ./build.gradle | awk -v OFS=' ' '{print $3}' | sed 's/"//g')
project_name=$(grep "^rootProject.name" ./settings.gradle | awk -F "=" '{print $2}' | sed 's/"//g')

# TODO Set port for project
export PORT=8081
export PROJECT_NAME=$project_name

# Set MySQL env vars
export HOST_IP=$hostIp
export MYSQL_CONTAINER_NAME=assignment_mysql
export MYSQL_DBNAME=assignment_mysql_user_service
export MYSQL_URL="jdbc:mysql://$HOST_IP:6606/$MYSQL_DBNAME?useSSL=false"
export MYSQL_USER=dbuser
export MYSQL_PASSWORD=dbpassword
#export CONSUL_HOST=$hostIp
#export CONSUL_PORT=8500
echo $MYSQL_CONTAINER_NAME
echo $MYSQL_DBNAME