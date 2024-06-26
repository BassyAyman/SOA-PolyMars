package com.masy.teamb.payloadservice.controllers;

import com.masy.teamb.payloadservice.controllers.dto.OrbitDataDTO;
import com.masy.teamb.payloadservice.controllers.dto.SatelliteMetricsDTO;
import com.masy.teamb.payloadservice.interfaces.IPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PayloadController {

    private static final Logger LOGGER = Logger.getLogger(PayloadController.class.getSimpleName());

    @Autowired
    private IPayload payload;

    @PostMapping(path = "orbitState", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> evaluateOrbitState(@RequestBody OrbitDataDTO orbitDataDTO){
        //LOGGER.log(Level.INFO, "Verifying orbit...");
        boolean result = payload.isOrbitRight(orbitDataDTO);
        if (!result){
            ResponseEntity.ok("NOT OK, bad orbit");
        }
        return ResponseEntity.ok("STOP"); // To stop receive data from telemetry service about rocket metrics
    }

    @PostMapping(path = "satelliteMetrics", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> storeSatelliteMetrics(@RequestBody SatelliteMetricsDTO satelliteMetricsDTO){
        try {
            payload.savePayloadMetricsToDB(satelliteMetricsDTO);
        } catch (Exception e) {
            return ResponseEntity.ok("FAILED TO STORE DATA");
        }
        return ResponseEntity.ok("OK");
    }

}
