#!/bin/bash

clear
echo "Following webcaster logs: "
# docker-compose logs --follow --since 0s
if ! docker-compose logs -f --tail 0 | grep "webcaster-service"; then
    sudo docker-compose logs -f --tail 0 | grep "webcaster-service"
fi