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
* `webcaster-service` deployed on `http://localhost:8091`
* `calculator-service` deployed on `http://localhost:8092`
* `astronaute-service` deployed on `http://localhost:8093`

## Scripts

* `prepare.sh` builds and starts all the services
* `run.sh` executes the rocket launch scenario

### Prepare.sh 
Make sure that the scripts have executable permissions.

You could use this command to add executable permissions to the scripts from the git clone directory:
```bash
find . -type f -name "*.sh" -exec chmod +x {} \;
```
This script is designed for Debian/Ubuntu system and will require modifications for other environments.
Ensure tmux is installed on your system. 

### Run.sh
The script divides the tmux window into multiple panes, each running a specific script:
* Top Left Pane: Runs log_displayIgor.sh to display the scenario logs.
* Top Right Pane: Runs log_webcaster.sh for webcaster specific logs.
* Bottom Left Pane: Executes follow-metrics.sh, showing real-time metrics of the rocket.
* Bottom Right Pane: Runs log_astronaute.sh, dedicated to astronaut metrics.
Additionally, the script creates a hidden session running the scenario.sh.
If it fails because your computer is a potato powered laptop... you may need it to start it manually while letting the log terminal opened.

### When Running
When the `run.sh` is executed, the first scenario is launched, during this one, the basics flow of success
events occurs, basically, the weather is good and the rocket have no problem, so we launch it, and 
after a while, the booster is detached and the payload after. After 30 second, the second 
scenario come next, with a rocket issue detected by telemetry that send message to commander,
so they order the destruction of the rocket. For more information, please consult the report available in the repository.

## Scenarios

### Scenario 1
The scenario consists in the following steps:
* Poll from the command center to check if everything is ok (weather and rocket)
* Rocket launch
* Booster stage from the rocket
* Satellite detachment
* Booster landing
* Simulated critical problem on the rocket (an external call directly on the rocket service)
* Order of command center to destroy the rocket and then auto-destruction (rocket-service auto-shutdown)

## Distribution of points (400 in total)
* Ayman Bassy: 100 points  
* Igor Melnyk: 100 points  
* Tobias Bonifay: 100 points  
* Mathieu Schalkwijk: 100 points
