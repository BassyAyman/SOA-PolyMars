#!/bin/bash

format_to_2f() {
    local input="$1"
    if [[ $input =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
        printf "%.2f" "$input"
    else
        echo "N/A"
    fi
}

send_get_request() {
    response=$(curl -s --connect-timeout 5 http://localhost:8082/rocketMetrics)

    # Check if the curl command was successful
    if [ $? -ne 0 ]; then
        altitude="Error"
        velocity="Error"
        fuelVolume="Error"
        elapsedTime="Error"
        isFine="Error"
    else
        # Extract the values
        altitude=$(echo "$response" | sed -n 's/.*"altitude":\([0-9.-]*\).*/\1/p')
        velocity=$(echo "$response" | sed -n 's/.*"velocity":\([0-9.-]*\).*/\1/p')
        fuelVolume=$(echo "$response" | sed -n 's/.*"fuelVolume":\([0-9.-]*\).*/\1/p')
        elapsedTime=$(echo "$response" | sed -n 's/.*"elapsedTime":\([0-9.-]*\).*/\1/p')
        isFine=$(echo "$response" | sed -n 's/.*"isFine":\([^}]*\).*/\1/p')

        # Format to two decimal places if possible
        altitude=$(format_to_2f "$altitude")
        velocity=$(format_to_2f "$velocity")
        fuelVolume=$(format_to_2f "$fuelVolume")
        elapsedTime=$(format_to_2f "$elapsedTime")
    fi

    # Display the result
    tput cr
    printf "Altitude: %s m | Velocity: %s m/s | Fuel: %s m^3 | Elapsed Time: %s s | IsRocketFine: %s" "$altitude" "$velocity" "$fuelVolume" "$elapsedTime" "$isFine"
}

clear
while true; do
    send_get_request
    sleep 0.1
done
