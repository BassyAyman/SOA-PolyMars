# SOA MARSY TEAM B

* Authors: Ayman Bassy, Igor Melnyk, Tobias Bonifay, Mathieu Schalkwijk

## List of services

* `weather-service` deployed on `http://localhost:8081`
* `rocket-service` deployed on `http://localhost:8082`
* `command-service` deployed on `http://localhost:8083`
* `launchpad-service` deployed on `http://localhost:8084`
* `payload-service` deployed on `http://localhost:8085`
* `telemetry-service` deployed on `http://localhost:8086`
* `staging-service` deployed on `http://localhost:8087`
* `satellite-service` deployed on `http://localhost:8088`
* `booster-service` deployed on `http://localhost:8089`

## Scripts

* `prepare.sh` builds and starts all the services
* `run.sh` executes the rocket launch scenario
* `follow-logs.sh` displays logs of all services from the moment the script is launched
* `follow-metrics.sh` display the metrics of the rocket in real time

#### For Windows users
* use `windows-prepare.sh` to build and starts all the services
* use `windows-run.sh` to execute the rocket launch scenario

## Distribution of points (400 in total)
* Ayman Bassy: 100 points  
* Igor Melnyk: 100 points  
* Tobias Bonifay: 100 points  
* Mathieu Schalkwijk: 100 points