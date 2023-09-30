package com.marsy.teamb.rocketservice.components;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BoosterProxy {

    private static final Logger LOGGER = Logger.getLogger(BoosterProxy.class.getSimpleName());

    private final static String BOOSTER_API_URL = "http://booster-service:8080";
    private RestTemplate restTemplate = new RestTemplate();

    public void dropBooster() {
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to boster-service: leave rocket");
        restTemplate.put(BOOSTER_API_URL + "/leaveRocket", null);
    }
}
