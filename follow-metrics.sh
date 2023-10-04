#!/bin/bash

BLACK='\033[1;30m'
DARK_RED='\033[0;31m'         # Dark Red
DARK_ORANGE='\033[0;91m'      # Dark Orange
DARK_GREEN='\033[0;32m'       # Dark Green
DARKER_GREEN='\033[0;92m'     # Darker Green
NC='\033[0m'

terminal_width=$(tput cols)
terminal_height=$(tput lines)

running=true

get_color_for_scale() {
    local value=$1
    local min=$2
    local max=$3

    if (( value < min )); then
        value=$min
    elif (( value > max )); then
        value=$max
    fi

    local range=$((max - min))
    local offset=$((value - min))

    # Custom color map for high contrast: red -> yellow -> green
    local color_map=(88 94 100 106 112 118 22)

    local map_idx=$(( (offset * ${#color_map[@]}) / range ))

    if (( map_idx < 0 )); then
        map_idx=0
    elif (( map_idx >= ${#color_map[@]} )); then
        map_idx=$((${#color_map[@]} - 1))
    fi

    color_idx=${color_map[$map_idx]}
    echo "\033[38;5;${color_idx}m"
}



format_to_2f() {
    local input="$1"
    if [[ $input =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
        printf "%.2f" "$input"
    else
        echo "Error"
    fi
}

format_to_int() {
    local input="$1"
    if [[ $input =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
        printf "%.0f" "$input"
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
        printf "${DARK_RED}%s${NC}" "$message"
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

        altitude=$(format_to_int "$altitude")
        velocity=$(format_to_int "$velocity")
        fuelVolume=$(format_to_int "$fuelVolume")
        elapsedTime=$(format_to_int "$elapsedTime")
        if [ "$isFine" == "false" ]; then
            isFine_color=$DARK_RED
        else
            isFine_color=$DARKER_GREEN
        fi

        altitude_color=$(get_color_for_scale "$altitude" 0 2000000)
        velocity_color=$(get_color_for_scale "$velocity" 0 30000)
        fuel_color=$(get_color_for_scale "$fuelVolume" 0 150)

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

# boum
tput bel
sleep 1
tput bel
