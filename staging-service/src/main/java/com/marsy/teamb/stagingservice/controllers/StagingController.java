package com.marsy.teamb.stagingservice.controllers;

import com.marsy.teamb.stagingservice.components.dto.FuelDataDTO;
import com.marsy.teamb.stagingservice.components.dto.KafkaProducerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class StagingController {

    private static final Logger LOGGER = Logger.getLogger(StagingController.class.getSimpleName());

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    KafkaProducerComponent producerComponent;

    @PostMapping(path = "/fuelState")
    public ResponseEntity<String> shouldWeStage(@RequestBody FuelDataDTO fuelDto) {
        if (fuelDto.getFuelVolume() <= 8) {
            //todo: call only once
            try {
                //LOGGER.log(Level.INFO, "[EXTERNAL CALL] to rocket-service: stage rocket");
                producerComponent.sendToCommandLogs("[EXTERNAL CALL] to rocket-service: stage rocket");
                restTemplate.put("http://rocket-service:8080/staging", null);
                return ResponseEntity.ok("Asked rocket to stage");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Could not call rocket service to stage rocket");
            }
        }
        return ResponseEntity.ok("Not staged");
    }

}
