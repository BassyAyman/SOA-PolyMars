package com.marsy.teamb.stagingservice.controllers;

import com.marsy.teamb.stagingservice.components.dto.FuelDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class StagingController {

    private static final Logger LOGGER = Logger.getLogger(StagingController.class.getSimpleName());

    @PostMapping(path = "fuelState", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> shouldWeStage(@RequestBody FuelDataDTO fuelDto) {
        LOGGER.log(Level.INFO, "");
        ResponseEntity.ok("Not staging");
        if (fuelDto.getFuelVolume() > 0.0) {
            LOGGER.log(Level.INFO, "No fuel. You trash");
        } else {
            LOGGER.log(Level.INFO, "Fuel..");
        }
        // TODO
        return ResponseEntity.ok("Staging");
    }

}
