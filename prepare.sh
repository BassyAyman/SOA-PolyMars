#!/bin/bash

# Stop script on errors
set -e

# 1. Prepare Weather Monitoring Service (Java/Spring Boot)
echo "Preparing Weather Monitoring Service..."
cd weather-service
mvn clean install
# for flash: pip install -r requirements.txt
# for Node.js: npm install
# for C++/websocket: make

# Start Docker containers for each service
echo "Starting Docker containers..."
# docker-compose up -d
