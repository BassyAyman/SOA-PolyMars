package com.marsy.teamb.boosterservice.controllers;

import com.marsy.teamb.boosterservice.dto.BoosterMetricsDTO;
import com.marsy.teamb.boosterservice.components.Sensors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = BoosterController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class BoosterController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(BoosterController.class.getSimpleName());

    @Autowired
    Sensors sensors;

    @GetMapping("/boosterMetrics")
    public ResponseEntity<BoosterMetricsDTO> rocketMetrics() {
        return ResponseEntity.ok(new BoosterMetricsDTO(sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime(), sensors.consultDetachState()));
    }

    @PutMapping("/leaveRocket")
    public ResponseEntity<Void> leaveRocket() {
        sensors.leaveRocket();
        return ResponseEntity.ok().build();
    }


}
