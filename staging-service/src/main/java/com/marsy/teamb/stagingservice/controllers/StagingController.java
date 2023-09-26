package com.marsy.teamb.stagingservice.controllers;

import com.marsy.teamb.stagingservice.components.dto.FuelDataDTO;
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

    private RestTemplate restTemplate = new RestTemplate();

    private boolean isRocketStaged = false;

    @PostMapping(path = "/fuelState")
    public ResponseEntity<String> shouldWeStage(@RequestBody FuelDataDTO fuelDto) {
        if (fuelDto.getFuelVolume() <= 8 && !isRocketStaged) {
            LOGGER.log(Level.INFO, "Call to rocket-service: stage rocket");
            isRocketStaged = true;
            restTemplate.put("http://rocket-service:8080/staging", null);
            return ResponseEntity.ok("Asked rocket to stage");
        }
        return ResponseEntity.ok("Not staged");
    }

}
