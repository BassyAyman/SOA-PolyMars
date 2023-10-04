#!/bin/bash

BLACK='\033[1;30m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
GREEN='\033[0;92m'
DARK_GREEN='\033[0;32m'
NC='\033[0m'

terminal_width=$(tput cols)
terminal_height=$(tput lines)

running=true


get_color_for_scale() {
    local value=$1

    if ! [[ $value =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
        printf '%s' "$NC"
        return
    fi

    local invert_scale=$2
    shift 2
    local -a breakpoints=("$@")
    local -a colors

    if [ "$invert_scale" == "true" ]; then
        colors=($BLACK $RED $YELLOW $GREEN $DARK_GREEN)
    else
        colors=($DARK_GREEN $GREEN $YELLOW $RED $BLACK)
    fi

    for ((i=0; i<${#breakpoints[@]}; i++)); do
        if (( $(echo "$value <= ${breakpoints[$i]}" | bc -l) )); then
            printf '%s' "${colors[$i]}"
            return
        fi
    done
    printf '%s' "${colors[-1]}"
}


format_to_2f() {
    local input="$1"
    if [[ $input =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
        printf "%.2f" "$input"
    else
        echo "Error"
    fi
}


send_get_request() {
    response=$(curl -s --connect-timeout 5 http://localhost:8082/rocketMetrics)

    if [ $? -ne 0 ]; then
        altitude="Error"
        velocity="Error"
        fuelVolume="Error"
        elapsedTime="Error"
        isFine="Error"
        tput cr
        tput el
        local message="Rocket is broken"
        printf "${RED}%s${NC}" "$message"
        printf '%*s' $((terminal_width - ${#message})) ''
        printf "\n"
        running=false
        return 1
    else
        altitude=$(echo "$response" | jq -r '.altitude')
        velocity=$(echo "$response" | jq -r '.velocity')
        fuelVolume=$(echo "$response" | jq -r '.fuelVolume')
        elapsedTime=$(echo "$response" | jq -r '.elapsedTime')
        isFine=$(echo "$response" | jq -r '.isFine')

        altitude=$(format_to_2f "$altitude")
        velocity=$(format_to_2f "$velocity")
        fuelVolume=$(format_to_2f "$fuelVolume")
        elapsedTime=$(format_to_2f "$elapsedTime")
        if [ "$isFine" == "false" ]; then
            isFine_color=$RED
        else
            isFine_color=$GREEN
        fi

        altitude_color=$(get_color_for_scale "$altitude" true 1 400000 700000 1000000 1500000)
        velocity_color=$(get_color_for_scale "$velocity" true 1 100 350 700 850)
        fuel_color=$(get_color_for_scale "$fuelVolume" true 0 20 50 90 155)

        tput cr
        printf "Altitude: ${altitude_color}%s m${NC} | " "$altitude"
        printf "Velocity: ${velocity_color}%s m/s${NC} | " "$velocity"
        printf "Fuel: ${fuel_color}%s m^3${NC} | " "$fuelVolume"
        printf "Elapsed Time: %s s | IsRocketFine: ${isFine_color}%s${NC} " "$elapsedTime" "$isFine"
    fi
}

clear
tput cr
while $running; do
    send_get_request
    sleep 0.1
done
