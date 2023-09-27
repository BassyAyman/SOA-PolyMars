#!/bin/bash

send_get_request() {
    response=$(curl -s http://localhost:8082/rocketMetrics)
    altitude=$(echo "$response" | sed -n 's/.*"altitude":\([^,]*\).*/\1/p')
    velocity=$(echo "$response" | sed -n 's/.*"velocity":\([^,]*\).*/\1/p')
    fuelVolume=$(echo "$response" | sed -n 's/.*"fuelVolume":\([^,]*\).*/\1/p')
    elapsedTime=$(echo "$response" | sed -n 's/.*"elapsedTime":\([^}]*\).*/\1/p')

    printf "\rAltitude: %.2f m | Velocity: %.2f m/s | Fuel: %.2f m^3 | Elapsed Time: %.2f s" "$altitude" "$velocity" "$fuelVolume" "$elapsedTime"
}

clear
while true; do
    send_get_request
    sleep 0.1
done
