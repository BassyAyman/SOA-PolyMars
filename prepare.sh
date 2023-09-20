#!/bin/bash

set -e

# Directory
if [ ! -d "app" ]; then
  mkdir app
else
  echo "Directory app already exists."
fi

# Check if Maven is installed; install if not
if ! command -v mvn &> /dev/null; then
    echo "Maven not found. Installing Maven..."
    sudo apt update
    sudo apt install -y maven
else
    echo "Maven is already installed."
fi

# Check if Docker is installed; install if not
if ! command -v docker &> /dev/null; then
    echo "Docker not found. Installing Docker..."
    sudo apt update
    sudo apt install -y docker.io
    sudo systemctl enable --now docker
else
    echo "Docker is already installed."
fi

echo "Preparing Weather Monitoring Service..."
cd weather-service
mvn clean install

cd ..

echo "Preparing Rocket Monitoring Service..."
cd rocket-service
mvn clean install

cd ..

echo "Preparing Command Service..."
cd command-service
mvn clean install

cd ..

echo "Preparing Launcher Pad Service..."
cd launchpad-service
mvn clean install

cd ..

echo "Preparing Payload Service..."
cd payload-service
mvn clean install

cd ..

echo "Starting Docker containers..."
docker-compose up --build -d

if [ -e "framework.sh" ]; then
  chmod +x ./framework.sh
  source ./framework.sh
else echo "framework.sh not found."
fi

wait_on_health http://localhost:8081
wait_on_health http://localhost:8082
wait_on_health http://localhost:8083
wait_on_health http://localhost:8084
wait_on_health http://localhost:8085