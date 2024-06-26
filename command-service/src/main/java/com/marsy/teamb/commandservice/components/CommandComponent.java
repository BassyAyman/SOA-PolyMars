package com.marsy.teamb.commandservice.components;

import com.marsy.teamb.commandservice.controllers.CommandController;
import com.marsy.teamb.commandservice.interfaces.ICommand;
import com.marsy.teamb.commandservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final static CustomLogger DISPLAY = new CustomLogger(CommandController.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private KafkaProducerComponent producerComponent;

    public String readinessPoll() {
        // Weather
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to weather-service: readiness check");
        DISPLAY.logIgor("[EXTERNAL CALL] to weather-service: readiness check");
        ResponseEntity<String> response = restTemplate.getForEntity(WEATHER_SERVICE + "/checkWeather", String.class);
        if (!Objects.equals(response.getBody(), "OK")) {
            return "NO GO - Something wrong with weather !";
        }
        // Launchpad - Rocket
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to launchpad-service: readiness check");
        DISPLAY.logIgor("[EXTERNAL CALL] to launchpad-service: readiness check");
        response = restTemplate.getForEntity(LAUNCHPAD_SERVICE + "/rocketCheck", String.class);
        if (!Objects.equals(response.getBody(), "OK")) {
            return "NO GO - Something wrong with rocket or launchpad !";
        }
        return "GO Order, everything ok";
    }

    public void launchRocket() {
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to launchpad-service: launch rocket");
        DISPLAY.logIgor("[EXTERNAL CALL] to launchpad-service: launch rocket");
        restTemplate.put(LAUNCHPAD_SERVICE+"/launchRocket",null);
    }

    @Override
    public void processDestruction() {
        LOGGER.log(Level.INFO, "Received critical rocket health");
        DISPLAY.logIgor("Received critical rocket health");
        try {
            LOGGER.log(Level.INFO, "[EXTERNAL CALL] to rocket-service: autodestruction");
            DISPLAY.logIgor("[EXTERNAL CALL] to rocket-service: autodestruction");
            restTemplate.put(ROCKET_SERVICE+"/destroy", "Detach request");
            producerComponent.sendMessageCaster("oh no, an issue has occurred, and the rocket has exposed, cringe");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while receiving autodestruction response of rocket-service");
            DISPLAY.logIgor("Error while receiving autodestruction response of rocket-service");
        }
    }

}
