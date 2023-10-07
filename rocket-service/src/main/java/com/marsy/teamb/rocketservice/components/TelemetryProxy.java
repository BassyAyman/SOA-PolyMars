package com.marsy.teamb.rocketservice.components;

import com.marsy.teamb.rocketservice.controllers.dto.RocketMetricsDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TelemetryProxy {

    @Autowired
    Sensors sensors;

    private static final Logger LOGGER = Logger.getLogger(TelemetryProxy.class.getSimpleName());

    private final static String TELEMETRY_API_URL = "http://telemetry-service:8080";

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        sendMetricsToTelemetryService(); // Executed once after component build
    }

    /**
     * Send metrics to telemetry every 1000ms
     */
    @Scheduled(fixedRate = 1000)
    public void sendMetricsToTelemetryService() {
        if (sensors.isBoosterDropped() && sensors.isPayloadDropped()) {
            return; //do not send data when payload and booster detached
        }
        LOGGER.log(Level.INFO, "Sending metrics to telemetry service");
        try {
            restTemplate.postForEntity(TELEMETRY_API_URL + "/rocketMetrics",
                    new RocketMetricsDTO(
                            sensors.consultAltitude(),
                            sensors.consultVelocity(),
                            sensors.consultFuelVolume(),
                            sensors.consultElapsedTime(),
                            sensors.consultPressure(),
                            sensors.isFine()
                    ), null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while sending metrics to telemetry service. Verify if the service is running.");
        }
    }
}
