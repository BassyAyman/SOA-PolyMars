package com.marsy.teamb.rocketservice.components;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SatelliteProxy {

    private static final Logger LOGGER = Logger.getLogger(SatelliteProxy.class.getSimpleName());

    private final static String SATELLITE_API_URL = "http://satellite-service:8080";
    private RestTemplate restTemplate = new RestTemplate();

    public void dropSatellite() {
        LOGGER.log(Level.INFO, "Call to satellite-service: leave rocket");
        restTemplate.put(SATELLITE_API_URL + "/leaveRocket", null);
    }
}
