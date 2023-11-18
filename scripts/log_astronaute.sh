#!/bin/bash

# URL of the astroHealthMetrics endpoint
URL="http://localhost:8093/astroHealthMetrics"
# Terminal width for formatting
terminal_width=$(tput cols)
# Color definitions
DARK_RED='\033[0;31m'
NC='\033[0m' # No Color

# Clear the terminal before starting
clear

# Flag to control the loop
running=true

# Start the loop
while $running; do
    # Fetch the data from the endpoint
    response=$(curl -s --connect-timeout 5 $URL)

    # Check if curl command was successful
    if [ $? -ne 0 ]; then
        # Handle error
        tput cr
        tput el
        message="Astronaut is dead"
        printf "${DARK_RED}%s${NC}" "$message"
        printf '%*s' $((terminal_width - ${#message})) ''
        printf "\n"
        running=false
        return 1
    else
        # Parse and format the JSON response
        missionID=$(echo "$response" | jq -r '.missionID')
        name=$(echo "$response" | jq -r '.name')
        heartbeats=$(echo "$response" | jq -r '.heartbeats')
        bloodPressure=$(echo "$response" | jq -r '.bloodPressure')
        elapsedTime=$(echo "$response" | jq -r '.elapsedTime')

        # Display the formatted data
        printf "Mission ID: %s | Astronaut: %s | Heart Beats: %s | Blood Pressure: %s | Elapsed Time: %s\n" \
               "$missionID" "$name" "$heartbeats" "$bloodPressure" "$elapsedTime"
    fi

    # Wait for 0.1 seconds before the next request
    sleep 0.1
done
