package com.marsy.teamb.rocketservice.components;

import com.marsy.teamb.rocketservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BoosterProxy {

    private static final Logger LOGGER = Logger.getLogger(BoosterProxy.class.getSimpleName());

    private static final CustomLogger DISPLAY = new CustomLogger(BoosterProxy.class);

    private final static String BOOSTER_API_URL = "http://booster-service:8080";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private KafkaProducerComponent producerComponent;

    public void dropBooster() {
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to boster-service: leave rocket");
        DISPLAY.logIgor("[EXTERNAL CALL] to boster-service: leave rocket");
        producerComponent.sendToCommandLogs("[EXTERNAL CALL] to boster-service: leave rocket");
        restTemplate.put(BOOSTER_API_URL + "/leaveRocket", null);
    }
}
