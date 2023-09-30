# SOA MARSY TEAM B

* Authors: Ayman Bassy, Igor Melnyk, Tobias Bonifay, Mathieu Schalkwijk

## List of services

* `weather-service` deployed on `http://localhost:8081`
* `rocket-service` deployed on `http://localhost:8082`
* `command-service` deployed on `http://localhost:8083`
* `launchpad-service` deployed on `http://localhost:8090`
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

## When Running
When the `run.sh` is executed, the first scenario is launched, during this one, the basics flow of success
events occurs, basically, the weather is good and the rocket have no problem, so we launch it, and 
after a while, the booster is detached and the payload after. After 30 second, the second 
scenario come next, with a rocket issue detected by telemetry that send message to commander,
so they order the destruction of the rocket.

## Distribution of points (400 in total)
* Ayman Bassy: 100 points  
* Igor Melnyk: 100 points  
* Tobias Bonifay: 100 points  
* Mathieu Schalkwijk: 100 points