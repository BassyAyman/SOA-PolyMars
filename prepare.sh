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
    sudo apt-get remove containerd.io
    sudo apt install docker.io docker-compose -y
    sudo systemctl enable --now docker
    sudo systemctl start docker
    sudo usermod -aG docker "$USER"
    echo "gpasswd -a $USER docker"
    sudo gpasswd -a "$USER" docker
    echo "Docker installed."
else
    echo "Docker is already installed."
fi

if ! command -v tmux > /dev/null; then
  if command -v apt-get > /dev/null; then
    sudo apt-get install -y tmux
  elif command -v yum > /dev/null; then
    sudo yum install -y tmux
  elif command -v pacman > /dev/null; then
    sudo pacman -S -y tmux
  elif command -v brew > /dev/null; then
    brew install -y tmux
  else
    echo "No package manager found. Cannot install tmux."
    exit 1
  fi
fi

echo "$BASH_VERSION"

function compile_dir() {
  echo "Preparing $1..."
    cd "$1"
    mvn clean package -DskipTests
    cd ..
}

echo "Compiling services..."
compile_dir "weather-service"
compile_dir "rocket-service"
compile_dir "command-service"
compile_dir "launchpad-service"
compile_dir "payload-service"
compile_dir "telemetry-service"
compile_dir "staging-service"
compile_dir "satellite-service"
compile_dir "booster-service"
echo "Services compiled."

echo "Starting Docker containers..."
docker-compose up --build -d
echo "Docker containers started."

function wait_on_health()  # $1 is URL of the Spring service with actuator on, $2 is the service name
{
   until [ $(curl --silent "$1"/actuator/health | grep UP -c ) == 1 ]
   do
      echo "Service $2 is not connected yet at $1"
      sleep 3
   done
   echo "Service $2 is up and running at $1"
}

echo "Waiting for services to start..."
wait_on_health http://localhost:8081 weather-service
wait_on_health http://localhost:8082 rocket-service
wait_on_health http://localhost:8083 command-service
wait_on_health http://localhost:8090 launchpad-service
wait_on_health http://localhost:8085 payload-service
wait_on_health http://localhost:8086 telemetry-service
wait_on_health http://localhost:8087 staging-service
wait_on_health http://localhost:8088 satellite-service
wait_on_health http://localhost:8089 booster-service
echo "All services are up and running."