package com.marsy.teamb.rocketservice.controllers;

import com.marsy.teamb.rocketservice.components.BoosterProxy;
import com.marsy.teamb.rocketservice.components.KafkaProducerComponent;
import com.marsy.teamb.rocketservice.components.SatelliteProxy;
import com.marsy.teamb.rocketservice.components.Sensors;
import com.marsy.teamb.rocketservice.controllers.dto.RocketMetricsDTO;
import com.marsy.teamb.rocketservice.logger.CustomLogger;
import com.marsy.teamb.rocketservice.services.SocketCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = RocketController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class RocketController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(RocketController.class.getSimpleName());

    private static final CustomLogger DISPLAY = new CustomLogger(RocketController.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Autowired
    Sensors sensors;

    @Autowired
    BoosterProxy boosterProxy;

    @Autowired
    SatelliteProxy satelliteProxy;

    @Autowired
    KafkaProducerComponent producerComponent;

    @Autowired
    SocketCom socketCom;

    @GetMapping("/rocketStatus")
    public ResponseEntity<String> rocketStatus() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        LOGGER.log(Level.INFO, "Rocket status is ok");
        DISPLAY.log("Rocket status is ok");
        producerComponent.sendToCommandLogs("Rocket status is ok");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/rocketMetrics")
    public ResponseEntity<RocketMetricsDTO> rocketMetrics() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        return ResponseEntity.ok(new RocketMetricsDTO(sensors.consultMissionID(), sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime(), sensors.consultPressure(), sensors.isFine()));
    }

    @PutMapping("/payloadDetach")
    public ResponseEntity<String> payloadDetach() {
        if (sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        sensors.detachPayload();
        LOGGER.log(Level.INFO, "Fairing separation...");
        DISPLAY.log("Fairing separation...");
        producerComponent.sendToCommandLogs("Fairing separation...");
        this.sensors.stopRocketEngine();
        LOGGER.log(Level.INFO, "Detaching payload...");
        producerComponent.sendToCommandLogs("Detaching payload...");
        DISPLAY.log("Mission ID is : " + sensors.consultMissionID());
        this.satelliteProxy.dropSatellite(sensors.consultMissionID()); // MissionID is sent here
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/launchRocket")
    public ResponseEntity<String> launchRocket() {
        LOGGER.log(Level.INFO, "Ignition...");
        DISPLAY.log("Ignition...");
        producerComponent.sendToCommandLogs("Ignition...");
        Sensors.startRocketClock();
        producerComponent.sendMissionIDToCommandService(sensors.consultMissionID());
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
        DISPLAY.log("There is a problem with the rocket");
        producerComponent.sendToCommandLogs("There is a problem with the rocket");
        this.sensors.detectProblem();
    }

    @PutMapping("/mockProblemOnVelocity")
    public void mockProblemTwo() {
        LOGGER.log(Level.INFO, "There is a problem with the velocity rocket");
        DISPLAY.log("There is a problem with the velocity rocket");
        producerComponent.sendToCommandLogs("There is a problem with the velocity rocket");
        this.sensors.mockVelocityGettingLess();
    }

    @PutMapping("/destroy")
    public void destroy() {
        if (sensors.isDestroyed()) {
            return;
        }
        LOGGER.log(Level.INFO, "Self-destruct...");
        DISPLAY.log("Self-destruct...");
        producerComponent.sendToCommandLogs("Self-destruct...");
        this.sensors.autoDestruct();
    }

    @PutMapping("/handleMaxQ")
    public ResponseEntity<String> handleMaxQ() {
        if (this.sensors.isDestroyed()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        if (Sensors.isMaxQReached()) {
            LOGGER.log(Level.INFO, "MaxQ reached...");
            Sensors.throttleDownEngine();
            return ResponseEntity.ok("Engine throttled down for Max Q phase.");
        }
        return ResponseEntity.ok("Max Q not reached.");
    }

    @PutMapping("/startInterview")
    public void startInterview() {
        executorService.execute(() -> socketCom.startCommunication()); // answer to interview in parallel to respond immediately to the put request
    }
}
