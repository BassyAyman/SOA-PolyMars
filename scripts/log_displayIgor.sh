#!/bin/bash

clear
echo "Following IGOR logs: "
# docker-compose logs --follow --since 0s
if ! docker-compose logs -f --tail 0 | grep "IGOR"; then
    sudo docker-compose logs -f --tail 0 | grep "IGOR";
fi