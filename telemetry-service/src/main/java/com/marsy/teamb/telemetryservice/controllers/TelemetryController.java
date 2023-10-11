package com.marsy.teamb.telemetryservice.controllers;

import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import com.marsy.teamb.telemetryservice.service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = TelemetryController.BASE_URI)
@CrossOrigin
@RestController
public class TelemetryController {
    static final String BASE_URI = "/";

    @Autowired
    private TelemetryService telemetryService;

    @PostMapping(path = "/rocketMetrics", consumes = APPLICATION_JSON_VALUE)
    public void processRocketTelemetry(@RequestBody RocketHardwareData dataRocketMetrics){
        telemetryService.processRocketTelemetry(dataRocketMetrics);
    }

    @PostMapping(path = "/boosterMetrics", consumes = APPLICATION_JSON_VALUE)
    public void processBoosterTelemetry(@RequestBody BoosterHardwareData dataBooster){
        telemetryService.processBoosterTelemetry(dataBooster);
    }
}
