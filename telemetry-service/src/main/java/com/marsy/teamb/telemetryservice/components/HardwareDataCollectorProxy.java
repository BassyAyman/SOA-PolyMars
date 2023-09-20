package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.HardwareRocketCollector;
import com.marsy.teamb.telemetryservice.modeles.HardwareData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HardwareDataCollectorProxy implements HardwareRocketCollector {
    private static final Logger LOGGER = Logger.getLogger(HardwareDataCollectorProxy.class.getSimpleName());
    private final static String ROCKET_API_SERVICE = "http://rocket-service:8080";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public HardwareData retrieveHardwareMetric() {
        LOGGER.log(Level.INFO, "Request to the Rocket the data about metrics ... ");
        ResponseEntity<HardwareData> collected =
                restTemplate.getForEntity(ROCKET_API_SERVICE+"/rocketMetrics", HardwareData.class);
        return collected.getBody();
    }
}
