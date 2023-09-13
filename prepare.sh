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
# For Python/Flask: pip install -r requirements.txt
# For Node.js: npm install
# For C++/WebSocket: make


