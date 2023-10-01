#!/bin/bash

clear
echo "Following logs for all services..."
# docker-compose logs --follow --since 0s
if ! docker-compose logs -f --tail 0; then
    sudo docker-compose logs -f --tail 0
fi