package com.marsy.teamb.launchpadservice.controllers;

import com.marsy.teamb.launchpadservice.components.LauncherPad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = LaunchController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@CrossOrigin
@RestController
public class LaunchController {

    public static final String BASE_URI = "/";
    @Autowired
    public LauncherPad pad;

    @GetMapping("/rocketCheck")
    public ResponseEntity<String> checkLaunching() {
        String resultOnLaunch = pad.canLaunchRocket();
        return ResponseEntity.ok(resultOnLaunch);
    }
}
