package com.marsy.teamb.launchpadservice.components;

import com.marsy.teamb.launchpadservice.interfaces.RocketProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LaucherRocketProxy implements RocketProxy {

    private final static String rocketApiUrl = "http://localhost:8082/";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String retrieveRocketStatus() {
        ResponseEntity<String> response = restTemplate.getForEntity(rocketApiUrl+"rocket-status", String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return "Rocket is not OK!";
        }
    }
}
