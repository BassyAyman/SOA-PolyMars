package com.marsy.teamb.satelliteservice.controllers;

import com.marsy.teamb.satelliteservice.components.Sensors;
import com.marsy.teamb.satelliteservice.dto.SatelliteMetricsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = SatelliteController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class SatelliteController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(SatelliteController.class.getSimpleName());

    @Autowired
    Sensors sensors;

    @GetMapping("/satelliteMetrics")
    public ResponseEntity<SatelliteMetricsDTO> rocketMetrics() {
        return ResponseEntity.ok(new SatelliteMetricsDTO(sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime(), sensors.consultDetachState()));
    }

    @PutMapping("/leaveRocket")
    public ResponseEntity<Void> leaveRocket() {
        sensors.leaveRocket();
        return ResponseEntity.ok().build();
    }


}
