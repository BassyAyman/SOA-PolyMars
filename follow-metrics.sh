#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
ORANGE='\033[0;34m'
BLUE='\033[0;35m'
LIGHT_GREEN='\033[1;32m'
LIGHT_BLUE='\033[1;34m'
DARK_BLUE='\033[0;36m'
NC='\033[0m' # No Color

send_get_request() {
    response=$(curl -s --connect-timeout 5 http://localhost:8082/rocketMetrics)

    if [ $? -ne 0 ] || [ -z "$response" ]; then
        altitude="Error"
        velocity="Error"
        fuelVolume="Error"
        elapsedTime="Error"
        isFine="Error"
    else
        altitude=$(echo "$response" | jq -r '.altitude')
        velocity=$(echo "$response" | jq -r '.velocity')
        fuelVolume=$(echo "$response" | jq -r '.fuelVolume')
        elapsedTime=$(echo "$response" | jq -r '.elapsedTime')
        isFine=$(echo "$response" | jq -r '.isFine')
    fi

    tput cr
#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
ORANGE='\033[0;34m'
BLUE='\033[0;35m'
NC='\033[0m' # No Color

send_get_request() {
    response=$(curl -s --connect-timeout 5 http://localhost:8082/rocketMetrics)

    if [ $? -ne 0 ] || [ -z "$response" ]; then
        altitude="Error"
        velocity="Error"
        fuelVolume="Error"
        elapsedTime="Error"
        isFine="Error"
    else
        altitude=$(echo "$response" | jq -r '.altitude')
        velocity=$(echo "$response" | jq -r '.velocity')
        fuelVolume=$(echo "$response" | jq -r '.fuelVolume')
        elapsedTime=$(echo "$response" | jq -r '.elapsedTime')
        isFine=$(echo "$response" | jq -r '.isFine')
    fi

    tput cr
    printf "Altitude: "
    [[ "$altitude" == "Error" ]] && printf "${RED}Error${NC}" || {
        (( $(echo "0 <= $altitude && $altitude <= 500000" | bc -l) )) && printf "${GREEN}%.2f m${NC}" "$altitude" || {
            (( $(echo "500001 <= $altitude && $altitude <= 1000000" | bc -l) )) && printf "${YELLOW}%.2f m${NC}" "$altitude" || {
                (( $(echo "1000001 <= $altitude && $altitude <= 1500000" | bc -l) )) && printf "${ORANGE}%.2f m${NC}" "$altitude" || printf "${RED}%.2f m${NC}" "$altitude"
            }
        }
    }

    printf " | Velocity: "
    [[ "$velocity" == "Error" ]] && printf "${RED}Error${NC}" || {
        (( $(echo "0 <= $velocity && $velocity <= 10000" | bc -l) )) && printf "${GREEN}%.2f m/s${NC}" "$velocity" || {
            (( $(echo "10001 <= $velocity && $velocity <= 20000" | bc -l) )) && printf "${YELLOW}%.2f m/s${NC}" "$velocity" || {
                (( $(echo "20001 <= $velocity && $velocity <= 30000" | bc -l) )) && printf "${ORANGE}%.2f m/s${NC}" "$velocity" || printf "${RED}%.2f m/s${NC}" "$velocity"
            }
        }
    }

    printf " | Fuel: "
    [[ "$fuelVolume" == "Error" ]] && printf "${RED}Error${NC}" || {
        (( $(echo "$fuelVolume > 70" | bc -l) )) && printf "${GREEN}%.2f m^3${NC}" "$fuelVolume" || {
            (( $(echo "40 <= $fuelVolume && $fuelVolume <= 70" | bc -l) )) && printf "${YELLOW}%.2f m^3${NC}" "$fuelVolume" || {
                (( $(echo "10 <= $fuelVolume && $fuelVolume <= 39" | bc -l) )) && printf "${ORANGE}%.2f m^3${NC}" "$fuelVolume" || printf "${RED}%.2f m^3${NC}" "$fuelVolume"
            }
        }
    }

    printf " | Elapsed Time: "
    [[ "$elapsedTime" == "Error" ]] && printf "${RED}Error${NC}" || printf "${BLUE}%.2f s${NC}" "$elapsedTime"

    printf " | IsRocketFine: "
    [[ "$isFine" == "Error" ]] && printf "${RED}Error${NC}" || printf "${GREEN}%s${NC}" "$isFine"
}

clear
while true; do
    send_get_request
    sleep 0.1
done


    printf " | Velocity: "
    [[ "$velocity" == "Error" ]] && printf "${RED}Error${NC}" || {
        (( $(echo "0 <= $velocity && $velocity <= 10000" | bc -l) )) && printf "${GREEN}%.2f m/s${NC}" "$velocity" || {
            (( $(echo "10001 <= $velocity && $velocity <= 20000" | bc -l) )) && printf "${YELLOW}%.2f m/s${NC}" "$velocity" || {
                (( $(echo "20001 <= $velocity && $velocity <= 30000" | bc -l) )) && printf "${ORANGE}%.2f m/s${NC}" "$velocity" || printf "${RED}%.2f m/s${NC}" "$velocity"
            }
        }
    }

    printf " | Fuel: "
    [[ "$fuelVolume" == "Error" ]] && printf "${RED}Error${NC}" || {
        (( $(echo "$fuelVolume > 70" | bc -l) )) && printf "${GREEN}%.2f m^3${NC}" "$fuelVolume" || {
            (( $(echo "40 <= $fuelVolume && $fuelVolume <= 70" | bc -l) )) && printf "${YELLOW}%.2f m^3${NC}" "$fuelVolume" || {
                (( $(echo "10 <= $fuelVolume && $fuelVolume <= 39" | bc -l) )) && printf "${ORANGE}%.2f m^3${NC}" "$fuelVolume" || printf "${RED}%.2f m^3${NC}" "$fuelVolume"
            }
        }
    }

    printf " | Elapsed Time: "
    [[ "$elapsedTime" == "Error" ]] && printf "${RED}Error${NC}" || printf "${BLUE}%.2f s${NC}" "$elapsedTime"

    printf " | IsRocketFine: "
    [[ "$isFine" == "Error" ]] && printf "${RED}Error${NC} " || printf "${GREEN}%s${NC}" "$isFine"
}

clear
while true; do
    send_get_request
    sleep 0.1
done
