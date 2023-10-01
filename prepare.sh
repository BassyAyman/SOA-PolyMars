#!/bin/bash

set -e

if [ ! -d "app" ]; then
  mkdir app
  echo "Directory app created."
else
  echo "Directory app already exists."
fi

if ! command -v curl > /dev/null; then
  sudo apt install -y curl
  if [ $? -ne 0 ]; then
    echo "Curl installation failed."
    exit 1
  else
    echo "Curl installed."
  fi
fi

if ! command -v wget > /dev/null; then
  sudo apt install -y wget
  if [ $? -ne 0 ]; then
    echo "Wget installation failed."
    exit 1
  else
    echo "Wget installed."
  fi
fi

install_java_17() {
    sudo apt install -y openjdk-17-jdk
    if [ $? -ne 0 ]; then
        sudo apt update -y && sudo apt upgrade -y && sudo apt install -y openjdk-17-jdk
        if [ $? -ne 0 ]; then
            sudo apt-get purge openjdk-\* -y
            sudo apt-get autoremove -y
            sudo apt-get autoclean

            sudo apt install -y openjdk-17-jdk
            if [ $? -ne 0 ]; then
                echo "Java 17 installation failed."
                exit 1
            fi
        fi
    fi
    echo "Java 17 installed."
}

if ! command -v java &> /dev/null; then
    echo "Java not found. Installing Java 17..."
    install_java_17
else
    if ! update-java-alternatives --list | grep -q "17"; then
        echo "Java 17 not found. Installing Java 17..."
        install_java_17
    else
        echo "Java 17 is available."
        if ! java -version 2>&1 | grep -q "17"; then
            echo "Setting Java 17 as the default Java version..."
            java_17_path=$(update-java-alternatives --list | grep "17" | awk '{print $3}')
            sudo update-alternatives --set java "$java_17_path"
            if [ $? -ne 0 ]; then
                echo "Failed to set Java 17 as the default Java version."
                exit 1
            fi
            echo "Java 17 is set as the default Java version."
        else
            echo "Java 17 is already the default Java version."
        fi
    fi
fi

if ! command -v mvn &> /dev/null; then
    echo "Maven not found. Trying to install using apt..."
    if sudo apt install -y maven; then
        echo "Maven installed using apt."
    else
        echo "Apt method didn't work. Installing Maven manually..."
        sudo apt-get install -y wget
        wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
        tar -xvf apache-maven-3.6.3-bin.tar.gz
        sudo mv apache-maven-3.6.3 /opt/
        rm apache-maven-3.6.3-bin.tar.gz
        if [ -f ~/.bashrc ]; then
            echo "export M2_HOME=/opt/apache-maven-3.6.3" >> ~/.bashrc
            echo "export PATH=$M2_HOME/bin:$PATH" >> ~/.bashrc
            source ~/.bashrc
        else
            echo "No bashrc found. Exporting to terminal..."
            export M2_HOME=/opt/apache-maven-3.6.3
            export PATH=$M2_HOME/bin:$PATH
        fi
        echo "Maven manually installed."
    fi
else
    echo "Maven is already installed."
fi


command -v docker &> /dev/null || {
    echo "Docker not found. Installing Docker..."
    sudo apt install -y docker.io
    if [ $? -ne 0 ]; then
        echo "Docker installation failed. Trying alternative method..."
        sudo apt-get purge docker-ce docker-ce-cli containerd.io -y
        sudo apt-get autoremove -y
        sudo apt-get autoclean

        sudo apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
        echo \
        "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
        $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
        sudo apt-get update -y
        sudo apt-get install -y docker-ce docker-ce-cli containerd.io
        if [ $? -ne 0 ]; then
            echo "Docker installation failed."
            exit 1
        fi
    fi
    sudo systemctl enable --now docker
    sudo usermod -aG docker "${USER:-$(whoami)}"
    echo "Docker installed."
}

command -v docker-compose &> /dev/null || {
  echo "Docker Compose not found. Installing Docker Compose...";
  sudo apt install -y docker-compose;
  if [ $? -ne 0 ]; then
    echo "Docker Compose installation failed."
    exit 1
  else
    echo "Docker Compose installed."
  fi
}


if ! command -v tmux > /dev/null; then
  if command -v apt-get > /dev/null; then
    sudo apt-get install -y tmux
    if [ $? -ne 0 ]; then
      echo "Tmux installation failed."
      exit 1
    else
      echo "Tmux installed."
    fi
  else
    echo "You must install tmux."
    exit 1
  fi
fi

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
if ! id -nG "$USER" | grep -qw docker; then
  sudo docker-compose up --build -d
else
  docker-compose up --build -d
fi
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