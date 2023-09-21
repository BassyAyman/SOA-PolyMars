package com.marsy.teamb.launchpadservice.controllers;

import com.marsy.teamb.launchpadservice.components.LauncherPad;
import com.marsy.teamb.launchpadservice.interfaces.RocketProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = LaunchController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@CrossOrigin
@RestController
public class LaunchController {

    static final String BASE_URI = "/";
    @Autowired
    private LauncherPad pad;

    @Autowired
    private RocketProxy rocketProxy;

    @GetMapping("/rocketCheck")
    public ResponseEntity<String> checkLaunching() {
        String resultOnLaunch = pad.canLaunchRocket();
        return ResponseEntity.ok(resultOnLaunch);
    }

    @PutMapping("/launchRocket")
    public void launchRocket() {
        rocketProxy.launchRocket();
    }
}
