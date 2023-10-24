package com.marsy.teamb.boosterservice.components;

import com.marsy.teamb.boosterservice.dto.BoosterMetricsDTO;
import com.marsy.teamb.boosterservice.logger.CustomLogger;
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

    private final static CustomLogger DISPLAY = new CustomLogger(TelemetryProxy.class);

    private final static String TELEMETRY_API_URL = "http://telemetry-service:8080";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Send metrics to telemetry every 1000ms
     */
    @Scheduled(fixedRate = 1000)
    public void sendMetricsToTelemetryService() {
        if (!sensors.consultDetachState()) {
            return;
        }
        // LOGGER.log(Level.INFO, "Sending metrics to telemetry service");
        // DISPLAY.logIgor("Sending metrics to telemetry service");
        try {
            restTemplate.postForEntity(TELEMETRY_API_URL + "/boosterMetrics",
                    new BoosterMetricsDTO(
                            sensors.consultAltitude(),
                            sensors.consultVelocity(),
                            sensors.consultFuelVolume(),
                            sensors.consultElapsedTime(),
                            sensors.consultDetachState()
                    ), null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while sending metrics to telemetry service. Verify if the service is running.");
            DISPLAY.log("Error while sending metrics to telemetry service. Verify if the service is running.");
        }
    }
}
