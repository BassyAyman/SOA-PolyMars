package com.marsy.teamb.rocketservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = RocketController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@CrossOrigin
@RestController
public class RocketController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(RocketController.class.getSimpleName());

    @GetMapping("/rocket-status")
    public ResponseEntity<String> rocketLaunch(){
        LOGGER.log(Level.INFO, "Rocket: Checking my status");
        LOGGER.log(Level.INFO, "Rocket: I'm ready to launch");
        return ResponseEntity.ok("OK");
    }
}
