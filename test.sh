#!/bin/bash

set -f

WEATHER_SERVICE="http://localhost:8081"
ROCKET_SERVICE="http://localhost:8082"
COMMAND_SERVICE="http://localhost:8083"
LAUNCHPAD_SERVICE="http://localhost:8090"
PAYLOAD_SERVICE="http://localhost:8085"
TELEMETRY_SERVICE="http://localhost:8086"
STAGING_SERVICE="http://localhost:8087"


test_get() {
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$1")

  if [[ "$RESPONSE" != "200" ]]; then
    echo "Error: $1 content: $RESPONSE"
    echo "$URL unsuccessful"
    exit 1
  fi

  CONTENT=$(curl -s "$1")
  if [[ "$CONTENT" != "$2" ]]; then
    echo "Error: $1 != '$2' content: $CONTENT"
    echo "$URL unsuccessful"
    exit 1
  fi

  echo "$1 successful"
}

# Fonction pour tester un service avec PUT
test_put() {
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$1")

  if [[ "$RESPONSE" != "200" ]]; then
    echo "Error: $1 content: $RESPONSE"
    echo "$URL unsuccessful"
    exit 1
  fi

  echo "$1 successful"
}

test_post() {
  URL=$1
  DATA=$2
  EXPECTED_RESPONSE=$3

  RESPONSE=$(curl -s -X POST -H "Content-Type: application/json" -d "$DATA" "$URL")

  if [[ "$RESPONSE" != "$EXPECTED_RESPONSE" ]]; then
    echo "Error: $URL != '$EXPECTED_RESPONSE'. Content $RESPONSE"
    echo "$URL unsuccessful"
    exit 1
  fi

  echo "$URL successful"
}



echo "--------------------"
echo "Weather Service..."
test_get "$WEATHER_SERVICE/checkWeather" "OK"
echo "--------------------"
echo "Launchpad Service..."
test_get "$LAUNCHPAD_SERVICE/rocketCheck" "OK"
echo "--------------------"
echo "Telemetry Service..."
test_put "$TELEMETRY_SERVICE/startTelemetryService"
echo "--------------------"
echo "Command Service..."
test_get "$COMMAND_SERVICE/launch" "GO Order, everything ok"
echo "--------------------"
#FUEL_DATA='{"fuelVolume":8}'
#echo "StagingService avec fuelVolume <= 8..."
#test_post "$STAGING_SERVICE/fuelState" "$FUEL_DATA" "Asked rocket to stage"
FUEL_DATA='{"fuelVolume":9}'
echo "StagingService avec fuelVolume > 8..."
test_post "$STAGING_SERVICE/fuelState" "$FUEL_DATA" "Not staged"
echo "--------------------"
echo "Test de RocketService /rocketStatus..."
test_get "$ROCKET_SERVICE/rocketStatus" "OK"
echo "Test de RocketService /rocketMetrics..."
EXPECTED_METRICS="{\"altitude\":0.0,\"velocity\":0.0,\"fuelVolume\":150.0,\"elapsedTime\":0.0,\"isFine\":true}"
test_get "$ROCKET_SERVICE/rocketMetrics" "$EXPECTED_METRICS"
echo "Test de RocketService /payloadDetach..."
test_put "$ROCKET_SERVICE/payloadDetach" "OK"
echo "Test de RocketService /launchRocket..."
test_put "$ROCKET_SERVICE/launchRocket" "OK"
echo "Test de RocketService /staging..."
test_put "$ROCKET_SERVICE/staging" "OK"
echo "--------------------"
EXPECTED_RESPONSE_OK="STOP"
EXPECTED_RESPONSE_NOT_OK="NOT OK, bad orbit"
ORBIT_DATANOK='{"altitude":2000,"velocity":7500}'
ORBIT_DATAOK='{"altitude":160000,"velocity":1000}'
echo "PayloadService..."
#test_post "$PAYLOAD_SERVICE/orbitState" "$ORBIT_DATAOK" "$EXPECTED_RESPONSE_OK"
#test_post "$PAYLOAD_SERVICE/orbitState" "$ORBIT_DATANOK" "$EXPECTED_RESPONSE_NOT_OK"

echo "--------------------"
echo "End of tests"