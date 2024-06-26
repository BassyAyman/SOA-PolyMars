package com.marsy.teamb.rocketservice.components;

import com.marsy.teamb.rocketservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SatelliteProxy {

    private static final Logger LOGGER = Logger.getLogger(SatelliteProxy.class.getSimpleName());

    private static final CustomLogger DISPLAY = new CustomLogger(SatelliteProxy.class);

    private final static String SATELLITE_API_URL = "http://satellite-service:8080";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private KafkaProducerComponent producerComponent;

    public void dropSatellite(String missionID) {
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to satellite-service: leave rocket");
        DISPLAY.log("[EXTERNAL CALL] to satellite-service: leave rocket");
        producerComponent.sendToCommandLogs("[EXTERNAL CALL] to satellite-service: leave rocket");

        HttpEntity<String> requestEntity = new HttpEntity<>(missionID);
        restTemplate.exchange(SATELLITE_API_URL + "/leaveRocket", HttpMethod.PUT, requestEntity, Void.class);
        //restTemplate.put(SATELLITE_API_URL + "/leaveRocket", missionID);
    }
}
