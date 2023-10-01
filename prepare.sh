#!/bin/bash
set -e

create_directory() {
    if [ ! -d "$1" ]; then
        mkdir "$1"
        echo "Directory $1 created."
    else
        echo "Directory $1 already exists."
    fi
}

install_package() {
    if ! command -v "$1" > /dev/null; then
        sudo apt install -y "$1" || { echo "$1 installation failed."; exit 1; }
        echo "$1 installed."
    else
        echo "$1 is already installed."
    fi
}

install_java_17() {
    if ! command -v java &> /dev/null || ! update-java-alternatives --list | grep -q "17"; then
        sudo apt install -y openjdk-17-jdk || {
            sudo apt update -y && sudo apt upgrade -y && sudo apt install -y openjdk-17-jdk || {
                sudo apt-get purge openjdk-\* -y && sudo apt-get autoremove -y && sudo apt-get autoclean && sudo apt install -y openjdk-17-jdk || {
                    echo "Java 17 installation failed."
                    exit 1
                }
            }
        }
        echo "Java 17 installed."
    else
        echo "Java 17 is available."
    fi
}

install_maven() {
    if command -v mvn &> /dev/null; then
        version=$(mvn -v | grep -m1 "Apache Maven" | awk '{print $3}')
        if [[ $(echo "$version 3.7" | awk '{print ($1 < $2)}') -eq 1 ]]; then
            echo "Maven version is less than 3.7. Could get errors."
            echo "export MAVEN_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED'" >> ~/.bashrc
            export MAVEN_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED'
            source ~/.bashrc
        fi
        echo "Maven is already installed."
    else
        sudo apt-get install -y wget
        wget https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.6/apache-maven-3.8.6-bin.tar.gz
        tar -xvf apache-maven-3.8.6-bin.tar.gz
        sudo mv apache-maven-3.8.6 /opt/
        rm apache-maven-3.8.6-bin.tar.gz
        if [ -f ~/.bashrc ]; then
            echo "export M2_HOME=/opt/apache-maven-3.8.6" >> ~/.bashrc
            echo "export PATH=$M2_HOME/bin:$PATH" >> ~/.bashrc
            source ~/.bashrc
            echo "Maven manually installed."
        else
            export M2_HOME=/opt/apache-maven-3.8.6
            export PATH=$M2_HOME/bin:$PATH
            echo "Maven manually installed without bashrc."
        fi
        if ! command -v mvn &> /dev/null; then
            sudo apt install -y maven
            if command -v mvn &> /dev/null; then
                echo "Maven installed via apt-get."
                echo "export MAVEN_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED'" >> ~/.bashrc
                export MAVEN_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED'
                source ~/.bashrc
            else
                echo "Failed to install Maven."
            fi
        fi
    fi
}


install_docker() {
    if ! command -v docker &> /dev/null; then
        sudo apt install -y docker.io || {
            sudo apt-get purge docker-ce docker-ce-cli containerd.io -y && sudo apt-get autoremove -y && sudo apt-get autoclean
            sudo apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release
            curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
            echo \
            "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
            $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
            sudo apt-get update -y
            sudo apt-get install -y docker-ce docker-ce-cli containerd.io || { echo "Docker installation failed."; exit 1; }
        }
        sudo systemctl enable --now docker
        sudo usermod -aG docker "${USER:-$(whoami)}"
        newgrp docker # to avoid logout/login
        echo "Docker installed."
    else
        echo "Docker is already installed."
    fi
}

install_docker_compose() {
    if ! command -v docker-compose &> /dev/null; then
        sudo apt install -y docker-compose || { echo "Docker Compose installation failed."; exit 1; }
        echo "Docker Compose installed."
    else
        echo "Docker Compose is already installed."
    fi
}

compile_dir() {
    echo "Preparing $1..."
    (cd "$1" && mvn clean package -DskipTests)
}

wait_on_health() {
    until [ "$(curl --silent "$1"/actuator/health | grep UP -c)" == 1 ]; do
        echo "Service $2 is not connected yet at $1"
        sleep 3
    done
    echo "Service $2 is up and running at $1"
}

create_directory "app"
install_package "curl"
install_package "wget"
install_package "tmux"
install_java_17
install_maven
install_docker
install_docker_compose

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