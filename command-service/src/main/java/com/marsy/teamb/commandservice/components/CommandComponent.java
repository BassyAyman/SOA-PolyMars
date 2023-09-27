package com.marsy.teamb.commandservice.components;

import com.marsy.teamb.commandservice.controllers.CommandController;
import com.marsy.teamb.commandservice.interfaces.ICommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CommandComponent implements ICommand {

    final static String TELEMETRY_SERVICE = "http://telemetry-service:8080";
    final static String WEATHER_SERVICE = "http://weather-service:8080";
    final static String LAUNCHPAD_SERVICE = "http://launchpad-service:8080";
    final static String ROCKET_SERVICE = "http://rocket-service:8080";

    private static final Logger LOGGER = Logger.getLogger(CommandController.class.getSimpleName());
    private final RestTemplate restTemplate = new RestTemplate();

    public String readinessPoll() {
        // Telemetry
        LOGGER.log(Level.INFO, "Start the Telemetry monitoring");
        restTemplate.put(TELEMETRY_SERVICE+"/startTelemetryService",null);
        // Weather
        LOGGER.log(Level.INFO, "Call to weather-service: readiness check");
        ResponseEntity<String> response = restTemplate.getForEntity(WEATHER_SERVICE + "/checkWeather", String.class);
        if (!Objects.equals(response.getBody(), "OK")) {
            return "NO GO - Something wrong with weather !";
        }
        // Launchpad - Rocket
        LOGGER.log(Level.INFO, "Call to launchpad-service: readiness check");
        response = restTemplate.getForEntity(LAUNCHPAD_SERVICE + "/rocketCheck", String.class);
        if (!Objects.equals(response.getBody(), "OK")) {
            return "NO GO - Something wrong with rocket or launchpad !";
        }
        return "GO Order, everything ok";
    }

    public void launchRocket() {
        LOGGER.log(Level.INFO, "Call to launchpad-service: launch rocket");
        restTemplate.put(LAUNCHPAD_SERVICE+"/launchRocket",null);
    }

    @Override
    public void processVerificationDestruction(boolean orderToDestroy) {
        if(!orderToDestroy){
            restTemplate.put(ROCKET_SERVICE+"/destroy",null);
        }
    }

}
