#!/bin/bash

send_get_request() {
    response=$(curl -s http://localhost:8082/rocketMetrics)

    # Extract the values
    altitude=$(echo "$response" | sed -n 's/.*"altitude":\([^,]*\).*/\1/p')
    velocity=$(echo "$response" | sed -n 's/.*"velocity":\([^,]*\).*/\1/p')
    fuelVolume=$(echo "$response" | sed -n 's/.*"fuelVolume":\([^,]*\).*/\1/p')
    elapsedTime=$(echo "$response" | sed -n 's/.*"elapsedTime":\([^,]*\).*/\1/p')
    isFine=$(echo "$response" | sed -n 's/.*"isFine":\([^}]*\).*/\1/p')

    # Format to two decimal places, capture the result, and keep as string
    altitude=$(printf "%.2f" "$altitude")
    velocity=$(printf "%.2f" "$velocity")
    fuelVolume=$(printf "%.2f" "$fuelVolume")
    elapsedTime=$(printf "%.2f" "$elapsedTime")

    # Display the result
    tput cr
    printf "Altitude: %s m | Velocity: %s m/s | Fuel: %s m^3 | Elapsed Time: %s s | IsRocketFine: %s" "$altitude" "$velocity" "$fuelVolume" "$elapsedTime" "$isFine"
}

clear
while true; do
    send_get_request
    sleep 0.1
done
