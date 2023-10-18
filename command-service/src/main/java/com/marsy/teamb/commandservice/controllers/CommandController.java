package com.marsy.teamb.commandservice.controllers;

import com.marsy.teamb.commandservice.components.KafkaProducerComponent;
import com.marsy.teamb.commandservice.interfaces.ICommand;
import com.marsy.teamb.commandservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class CommandController {

    private static final Logger LOGGER = Logger.getLogger(CommandController.class.getSimpleName());
    private static final CustomLogger DISPLAY = new CustomLogger(CommandController.class);

    @Autowired
    private ICommand command;

    @Autowired
    KafkaProducerComponent producerComponent;

    @GetMapping("/startMission")
    public ResponseEntity<String> rocketLaunch() {
        producerComponent.startMission(new Date());
        LOGGER.log(Level.INFO, "Preparing for launching...");
        DISPLAY.logIgor("Preparing for launching...");
        String result = command.readinessPoll();
        LOGGER.log(Level.INFO, "Command center decision: \"" + result + "\"");
        DISPLAY.logIgor("Command center decision: \"" + result + "\"");
        if (result.equals("GO Order, everything ok")) {
            command.launchRocket();
        }
        return ResponseEntity.ok(result);
    }
}
