#!/bin/bash

sleep 5
echo "Start first mission at $(date +"%T")"

sleep 1
curl http://localhost:8083/startMission

echo -e "Wait for 35 seconds to simulate a problem with the rocket... at $(date +"%T")"
sleep 35

echo "Simulating a problem with the rocket... at $(date +"%T")"
curl -X PUT http://localhost:8082/mockProblem

sleep 10

echo -e "Start second mission at $(date +"%T")"
curl http://localhost:8083/startMission
