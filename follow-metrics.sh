#!/bin/bash

send_get_request() {
    response=$(curl -s http://localhost:8082/rocketMetrics)
    altitude=$(echo "$response" | sed -n 's/.*"altitude":\([^,]*\).*/\1/p')
    velocity=$(echo "$response" | sed -n 's/.*"velocity":\([^,]*\).*/\1/p')
    fuelVolume=$(echo "$response" | sed -n 's/.*"fuelVolume":\([^,]*\).*/\1/p')
    elapsedTime=$(echo "$response" | sed -n 's/.*"elapsedTime":\([^,]*\).*/\1/p')
    isFine=$(echo "$response" | sed -n 's/.*"isFine":\([^}]*\).*/\1/p')
    tput cr
    printf "Altitude: %.2f m | Velocity: %.2f m/s | Fuel: %.2f m cubed | Elapsed Time: %.2f s | IsRocketFine: %s" "$altitude" "$velocity" "$fuelVolume" "$elapsedTime" "$isFine"
}


clear
while true; do
    send_get_request
    sleep 0.1
done
