package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.controllers.dto.SatelliteMetricsDTO;
import com.masy.teamb.payloadservice.interfaces.IPayloadProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PayloadProxy implements IPayloadProxy {

    private static final Logger LOGGER = Logger.getLogger(PayloadProxy.class.getSimpleName());
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    KafkaProducerComponent producerComponent;

    @Override
    public void sendDetachOrder() {
        LOGGER.log(Level.INFO, "[EXTERNAL CALL] to rocket-service: detach payload");
        producerComponent.sendToCommandLogs("[EXTERNAL CALL] to rocket-service: detach payload");
        try {
            restTemplate.put(ROCKET_SERVICE+"/payloadDetach", "Detach request");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while sending detach order to rocket-service");
        }
    }

    @Override
    public SatelliteMetricsDTO getSatelliteMetrics() {
        ResponseEntity<SatelliteMetricsDTO> metrics = restTemplate.getForEntity(SATELLITE_SERVICE+"/satelliteMetrics", SatelliteMetricsDTO.class);
        return metrics.getBody();
    }
}
