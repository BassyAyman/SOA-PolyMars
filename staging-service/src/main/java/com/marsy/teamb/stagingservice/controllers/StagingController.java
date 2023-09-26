package com.marsy.teamb.stagingservice.controllers;

import com.marsy.teamb.stagingservice.components.dto.FuelDataDTO;
import jakarta.validation.Valid;
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

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping(path = "/fuelState", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> shouldWeStage(@RequestBody @Valid FuelDataDTO fuelDto) {
        if (fuelDto.getFuelVolume() <= 8) {
            LOGGER.log(Level.INFO, "Call to rocket-service: stage rocket");
            restTemplate.put("http://rocket-service:8080/staging", null);
            return ResponseEntity.ok("Asked rocket to stage");
        }
        return ResponseEntity.ok("Not staged");
    }

}
