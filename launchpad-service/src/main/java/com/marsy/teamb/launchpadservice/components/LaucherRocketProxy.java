package com.marsy.teamb.launchpadservice.components;

import com.marsy.teamb.launchpadservice.interfaces.RocketProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LaucherRocketProxy implements RocketProxy {

    private static final Logger LOGGER = Logger.getLogger(LaucherRocketProxy.class.getSimpleName());

    private final static String rocketApiUrl = "http://rocket-service:8080";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ProducerCommand producerCommand;

    @Override
    public String retrieveRocketStatus() {

        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to rocket-service: asking for status");
        producerCommand.sendToCommandLogs("[EXTERNAL CALL] to rocket-service: asking for status");
//        ResponseEntity<String> response = restTemplate.getForEntity(rocketApiUrl + "/rocketStatus", String.class);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response.getBody();
//        } else {
//            return "Rocket is not OK!";
//        } TODO
        return "OK";
    }

    @Override
    public void launchRocket() {
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to rocket-service: launch rocket");
        producerCommand.sendToCommandLogs("[EXTERNAL CALL] to rocket-service: launch rocket");
        //restTemplate.put(rocketApiUrl+"/launchRocket", null); TODO
    }
}
