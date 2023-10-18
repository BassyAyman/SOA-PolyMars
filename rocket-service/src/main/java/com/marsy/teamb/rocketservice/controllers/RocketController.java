package com.marsy.teamb.rocketservice.controllers;

import com.marsy.teamb.rocketservice.components.BoosterProxy;
import com.marsy.teamb.rocketservice.components.KafkaProducerComponent;
import com.marsy.teamb.rocketservice.components.SatelliteProxy;
import com.marsy.teamb.rocketservice.components.Sensors;
import com.marsy.teamb.rocketservice.controllers.dto.RocketMetricsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    KafkaProducerComponent producerComponent;

    @GetMapping("/rocketStatus")
    public ResponseEntity<String> rocketStatus() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        LOGGER.log(Level.INFO, "Rocket status is ok");
        producerComponent.sendToCommandLogs("Rocket status is ok");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/rocketMetrics")
    public ResponseEntity<RocketMetricsDTO> rocketMetrics() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        return ResponseEntity.ok(new RocketMetricsDTO(sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime(), sensors.consultPressure(), sensors.isFine()));
    }

    @PutMapping("/payloadDetach")
    public ResponseEntity<String> payloadDetach() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        sensors.detachPayload();
        LOGGER.log(Level.INFO, "Fairing separation...");
        producerComponent.sendToCommandLogs("Fairing separation...");
        this.sensors.stopRocketEngine();
        LOGGER.log(Level.INFO, "Detaching payload...");
        producerComponent.sendToCommandLogs("Detaching payload...");
        this.satelliteProxy.dropSatellite();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/launchRocket")
    public ResponseEntity<String> launchRocket() {
        LOGGER.log(Level.INFO, "Ignition...");
        producerComponent.sendToCommandLogs("Ignition...");
        Sensors.startRocketClock();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/staging")
    public ResponseEntity<String> staging() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        if (sensors.isBoosterDropped()) {
            return ResponseEntity.ok("Booster already dropped");
        }
        this.boosterProxy.dropBooster();
        this.sensors.dropBooster();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/mockProblem")
    public void mockProblem() {
        LOGGER.log(Level.INFO, "There is a problem with the rocket");
        producerComponent.sendToCommandLogs("There is a problem with the rocket");
        this.sensors.detectProblem();
    }

    @PutMapping("/mockProblemOnVelocity")
    public void mockProblemTwo() {
        LOGGER.log(Level.INFO, "There is a problem with the velocity rocket");
        producerComponent.sendToCommandLogs("There is a problem with the velocity rocket");
        this.sensors.mockVelocityGettingLess();
    }

    @PutMapping("/destroy")
    public void destroy() {
        if (sensors.isDestroyed()) {
            return;
        }
        LOGGER.log(Level.INFO, "Self-destruct...");
        producerComponent.sendToCommandLogs("Self-destruct...");
        this.sensors.autoDestruct();
    }

    @PutMapping("/handleMaxQ")
    public ResponseEntity<String> handleMaxQ() {
        if (this.sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        if (this.sensors.isMaxQReached()) {
            LOGGER.log(Level.INFO, "MaxQ reached...");
            sensors.throttleDownEngine();
            return ResponseEntity.ok("Engine throttled down for Max Q phase.");
        }
        return ResponseEntity.ok("Max Q not reached.");
    }
}
