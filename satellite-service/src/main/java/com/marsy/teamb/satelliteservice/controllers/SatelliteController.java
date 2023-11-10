package com.marsy.teamb.satelliteservice.controllers;

import com.marsy.teamb.satelliteservice.SatelliteServiceApplication;
import com.marsy.teamb.satelliteservice.components.Sensors;
import com.marsy.teamb.satelliteservice.dto.SatelliteMetricsDTO;
import com.marsy.teamb.satelliteservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = SatelliteController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class SatelliteController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(SatelliteController.class.getSimpleName());
    private static final CustomLogger DISPLAY = new CustomLogger(SatelliteServiceApplication.class);

    @Autowired
    Sensors sensors;

    @GetMapping("/satelliteMetrics")
    public ResponseEntity<SatelliteMetricsDTO> rocketMetrics() {
        return ResponseEntity.ok(new SatelliteMetricsDTO(
                sensors.consultMissionID(), sensors.consultAltitude(), sensors.consultVelocity(), sensors.consultFuelVolume(), sensors.consultElapsedTime(), sensors.consultDetachState()));
    }

    @PutMapping("/leaveRocket")
    public ResponseEntity<String> leaveRocket(@RequestBody String missionID) {
        DISPLAY.log("Mission ID that was received by controller : " + missionID);
        sensors.setMissionID(missionID);
        sensors.leaveRocket();
        return ResponseEntity.ok("Detach message received");
    }


}
