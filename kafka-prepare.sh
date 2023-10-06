#!/bin/bash

set -e

function compile_dir()   # $1 is the dir to get it
{
  echo "Preparing $1..."
    cd $1
    mvn clean package -DskipTests
    cd ..
}

compile_dir "weather-service"
compile_dir "command-service"


echo "Starting Docker containers..."
docker-compose up --build -d zookeeper kafka weather-service command-service



function wait_on_health()  # $1 is URL of the Spring service with actuator on, $2 is the service name
{
   until [ $(curl --silent "$1"/actuator/health | grep UP -c ) == 1 ]
   do
      echo "Service $2 is not connected yet at $1"
      sleep 3
   done
   echo "Service $2 is up and running at $1"
}

wait_on_health http://localhost:8081 weather-service
wait_on_health http://localhost:8083 command-service