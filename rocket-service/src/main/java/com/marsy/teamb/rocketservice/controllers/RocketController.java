package com.marsy.teamb.rocketservice.controllers;

import com.marsy.teamb.rocketservice.components.BoosterProxy;
import com.marsy.teamb.rocketservice.components.SatelliteProxy;
import com.marsy.teamb.rocketservice.components.Sensors;
import com.marsy.teamb.rocketservice.controllers.dto.RocketMetricsDTO;
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

    @Autowired
    BoosterProxy boosterProxy;

    @Autowired
    SatelliteProxy satelliteProxy;

    @GetMapping("/rocketStatus")
    public ResponseEntity<String> rocketStatus() {
        LOGGER.log(Level.INFO, "Rocket status is ok");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/rocketMetrics")
    public ResponseEntity<RocketMetricsDTO> rocketMetrics() {
        return ResponseEntity.ok(new RocketMetricsDTO(sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime(), sensors.isFine()));
    }

    @PutMapping("/payloadDetach")
    public ResponseEntity<String> payloadDetach() {
        this.satelliteProxy.dropSatellite();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/launchRocket")
    public ResponseEntity<String> launchRocket() {
        LOGGER.log(Level.INFO, "Ignition...");
        Sensors.startRocketClock();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/staging")
    public ResponseEntity<String> staging() {
        LOGGER.log(Level.INFO, "Staging booster");
        this.boosterProxy.dropBooster();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/mockProblem")
    public void mockProblem() {
        LOGGER.log(Level.INFO, "There is a problem with the rocket");
        this.sensors.detectProblem();
    }

    @PutMapping("/destroy")
    public void destroy() {
        LOGGER.log(Level.INFO, "Autodestruction...");
        this.sensors.autoDestruct();
    }

}
