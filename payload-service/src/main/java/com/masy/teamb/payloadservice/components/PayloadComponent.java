package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.controllers.dto.OrbitDataDTO;
import com.masy.teamb.payloadservice.interfaces.IPayload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PayloadComponent implements IPayload {

    final static String ROCKET_SERVICE = "http://rocket-service:8080";
    private static final Logger LOGGER = Logger.getLogger(PayloadComponent.class.getSimpleName());
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean isOrbitRight(OrbitDataDTO orbitDataDTO) {
        // process calculations and decide if orbit is correct to send detach msg to Rocket Service
        if (orbitDataDTO.altitude() > 10000 && orbitDataDTO.velocity() > 7000){
            // Detach order to the Rocket Service
            LOGGER.log(Level.INFO, "[INTERNAL] Good orbit");
            LOGGER.log(Level.INFO, "[EXTERNAL CALL] rocket service call, detach order...");
            restTemplate.put(ROCKET_SERVICE+"/payloadDetach", "Detach request");
            return true;
        }
        return false;
    }
}
