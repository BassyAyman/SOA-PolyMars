package com.marsy.teamb.telemetryservice.controllers;

import com.marsy.teamb.telemetryservice.service.StartCollectingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = TelemetryController.BASE_URI)
@CrossOrigin
@Controller
public class TelemetryController {
    static final String BASE_URI = "/";

    @Autowired
    private StartCollectingData starterService;

    @PutMapping("/startTelemetryService")
    public void startTelemetryService(){
        starterService.startTelemtryService();
    }
}
