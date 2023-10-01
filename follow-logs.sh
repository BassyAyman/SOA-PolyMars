#!/bin/bash

clear
echo "Following logs for all services..."
# docker-compose logs --follow --since 0s

sudo docker-compose logs -f --tail 0