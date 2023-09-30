#!/bin/bash

echo "Test of rocket launch: call Command Service to launch rocket"
curl http://localhost:8083/launch

echo -e "Wait for 30 seconds to simulate a problem with the rocket..."
sleep 30
echo "Simulating a problem with the rocket..."
curl -X PUT http://localhost:8082/mockProblem
