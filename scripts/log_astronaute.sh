#!/bin/bash

clear
# docker-compose logs --follow --since 0s
if ! docker-compose logs -f --tail 0 | grep "austronaute-service" | grep "ASTRO" | sed 's/ASTRO//'; then
    # Using docker-compose to follow logs with no initial tail
    # and piping it to grep to filter lines containing "austronaute-service".
    # The output of this is then piped to a second grep to filter lines containing "ASTRO".
    # Finally, sed is used to remove the "ASTRO" part from those lines.
    sudo docker-compose logs -f --tail 0 | grep "austronaute-service" | sed 's/ASTRO//'
else
    echo "Astronaute service is not running"
fi