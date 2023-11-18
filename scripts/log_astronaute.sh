#!/bin/bash

clear
# docker-compose logs --follow --since 0s
if ! docker-compose logs -f --tail 0 | grep "astronaute-service"; then
    sudo docker-compose logs -f --tail 0 | grep "astronaute-service"
fi