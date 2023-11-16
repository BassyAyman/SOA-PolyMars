package com.marsy.teamb.rocketservice.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class AstronautProxy {
    private static final Logger LOGGER = Logger.getLogger(BoosterProxy.class.getSimpleName());

    private final static String ASTRONAUT_API_URL = "http://astronaute-service:8080";
    @Autowired
    private KafkaProducerComponent producerComponent;

    private final RestTemplate restTemplate = new RestTemplate();

    public void startAstroHealth() {
        LOGGER.info("start sending astronaut health status");
        producerComponent.sendToCommandLogs("start sending astronaut health status");
        restTemplate.put(ASTRONAUT_API_URL + "/startAstroHealth", null);
    }

    public void ejectAstronaut() {
        LOGGER.info("eject astronaut");
        producerComponent.sendToCommandLogs("eject astronaut");
        restTemplate.put(ASTRONAUT_API_URL + "/ejectAstronaut", null);
    }
}
