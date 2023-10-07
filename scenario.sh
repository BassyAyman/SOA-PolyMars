#!/bin/bash

sleep 5
echo "Test of rocket launch: call Command Service to launch rocket at $(date +"%T")"

sleep 1
curl http://localhost:8083/launch

echo "Wait for 35 seconds to simulate a problem with the rocket... at $(date +"%T")"
sleep 35

echo "Simulating a problem with the rocket... at $(date +"%T")"
curl -X PUT http://localhost:8082/mockProblem
