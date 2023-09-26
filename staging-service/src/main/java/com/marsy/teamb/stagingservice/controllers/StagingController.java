package com.marsy.teamb.stagingservice.controllers;

import com.marsy.teamb.stagingservice.components.dto.FuelDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class StagingController {

    private static final Logger LOGGER = Logger.getLogger(StagingController.class.getSimpleName());

    // rest template
    @Autowired
    private static final RestTemplate restTemplate = new RestTemplate();

    @PostMapping(path = "fuelState", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> shouldWeStage(@RequestBody FuelDataDTO fuelDto) {
        LOGGER.log(Level.INFO, "");
        ResponseEntity.ok("Not staging");
        if (fuelDto.getFuelVolume() <= 8.1) {
            LOGGER.log(Level.INFO, "No fuel. You trash");
            restTemplate.postForEntity("http://rocket-service:8080/staging", fuelDto, FuelDataDTO.class);
            return ResponseEntity.ok("Staging");
        } else {
            LOGGER.log(Level.INFO, "Fuel..");
        }
        restTemplate.postForEntity("http://rocket-service:8080/staging", fuelDto, FuelDataDTO.class);
        return ResponseEntity.ok("not staged");
    }

}
