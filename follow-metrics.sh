#!/bin/bash

format_to_2f() {
    local input="$1"
    # Check if the input is a valid float or integer
    if [[ $input =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
        printf "%.2f" "$input"
    else
        # Return the original input or "N/A" or any default value
        echo "$input"
    fi
}

send_get_request() {
    response=$(curl -s http://localhost:8082/rocketMetrics)

    # Extract the values
    altitude=$(echo "$response" | sed -n 's/.*"altitude":\([^,]*\).*/\1/p')
    velocity=$(echo "$response" | sed -n 's/.*"velocity":\([^,]*\).*/\1/p')
    fuelVolume=$(echo "$response" | sed -n 's/.*"fuelVolume":\([^,]*\).*/\1/p')
    elapsedTime=$(echo "$response" | sed -n 's/.*"elapsedTime":\([^,]*\).*/\1/p')
    isFine=$(echo "$response" | sed -n 's/.*"isFine":\([^}]*\).*/\1/p')

    # Format to two decimal places if possible
    altitude=$(format_to_2f "$altitude")
    velocity=$(format_to_2f "$velocity")
    fuelVolume=$(format_to_2f "$fuelVolume")
    elapsedTime=$(format_to_2f "$elapsedTime")

    # Display the result
    tput cr
    printf "Altitude: %s m | Velocity: %s m/s | Fuel: %s m^3 | Elapsed Time: %s s | IsRocketFine: %s" "$altitude" "$velocity" "$fuelVolume" "$elapsedTime" "$isFine"
}

clear
while true; do
    send_get_request
    sleep 0.1
done
