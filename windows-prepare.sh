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
compile_dir "rocket-service"
compile_dir "command-service"
compile_dir "launchpad-service"
compile_dir "payload-service"
compile_dir "telemetry-service"
compile_dir "staging-service"
compile_dir "satellite-service"
compile_dir "booster-service"
compile_dir "webcaster-service"
compile_dir "calculator-service"

echo "Starting Docker containers..."
docker-compose up --build -d
echo "waiting the database run"
sleep 5
# Configure PostgreSQL and restart containers
echo "Setting up PostgreSQL..."
docker exec -it telemetry-database psql -U postgres -d telemetry_db -c "CREATE USER reading_user WITH PASSWORD 'reading_pass';"
docker exec -it telemetry-database psql -U postgres -d telemetry_db -c "GRANT CONNECT ON DATABASE telemetry_db TO reading_user;"
docker exec -it telemetry-database psql -U postgres -d telemetry_db -c "GRANT SELECT ON ALL TABLES IN SCHEMA public TO reading_user;"
docker exec -it telemetry-database psql -U postgres -d telemetry_db -c "GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO reading_user;"
docker exec -it telemetry-database psql -U postgres -d telemetry_db -c "GRANT USAGE ON SCHEMA public TO reading_user;"
docker exec -it telemetry-database psql -U postgres -d telemetry_db -c "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO reading_user;"


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
wait_on_health http://localhost:8082 rocket-service
wait_on_health http://localhost:8083 command-service
wait_on_health http://localhost:8090 launchpad-service
wait_on_health http://localhost:8085 payload-service
wait_on_health http://localhost:8086 telemetry-service
wait_on_health http://localhost:8087 staging-service
wait_on_health http://localhost:8088 satellite-service
wait_on_health http://localhost:8089 booster-service
wait_on_health http://localhost:8090 webcaster-service
# wait_on_health http://localhost:8091 calculator-service