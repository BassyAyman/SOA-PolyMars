package com.marsy.teamb.rocketservice.controllers;

import com.marsy.teamb.rocketservice.components.Sensors;
import com.marsy.teamb.rocketservice.controllers.dto.MetricsDTO;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = RocketController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class RocketController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(RocketController.class.getSimpleName());

    @Autowired
    Sensors sensors;

    @GetMapping("/rocketStatus")
    public ResponseEntity<String> rocketStatus() {
        LOGGER.log(Level.INFO, "Rocket status is ok");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/rocketMetrics")
    public ResponseEntity<MetricsDTO> rocketMetrics() {
        return ResponseEntity.ok(new MetricsDTO(sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime()));
    }

    @PutMapping("/payloadDetach")
    public ResponseEntity<String> payloadDetach() {
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/launchRocket")
    public ResponseEntity<String> launchRocket() {
        Sensors.startRocketClock();
        return ResponseEntity.ok("Ignition");
    }

}
