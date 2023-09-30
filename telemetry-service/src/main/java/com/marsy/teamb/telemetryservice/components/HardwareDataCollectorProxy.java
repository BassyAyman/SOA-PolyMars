package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.HardwareDataCollector;
import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;


import java.util.logging.Logger;

@Component
public class HardwareDataCollectorProxy implements HardwareDataCollector {
    private static final Logger LOGGER = Logger.getLogger(HardwareDataCollectorProxy.class.getSimpleName());
    private final static String ROCKET_API_SERVICE = "http://rocket-service:8080";
    private final static String BOOSTER_API_SERVICE = "http://booster-service:8080";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public RocketHardwareData retrieveRocketHardwareMetric() {
        //LOGGER.log(Level.INFO, "Request to the Rocket the data about metrics ... ");
        try {
            ResponseEntity<RocketHardwareData> collected =
                    restTemplate.getForEntity(ROCKET_API_SERVICE+"/rocketMetrics", RocketHardwareData.class);
            return collected.getBody();
        } catch (Exception e) {
            LOGGER.severe("Error while retrieving rocket metrics");
            return null;
        }
    }

    @Override
    public BoosterHardwareData retrieveBoosterHardwareMetric() {
        ResponseEntity<BoosterHardwareData> collected =
                restTemplate.getForEntity(BOOSTER_API_SERVICE+"/boosterMetrics", BoosterHardwareData.class);
        return collected.getBody();
    }
}
