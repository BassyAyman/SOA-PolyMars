package com.marsy.teamb.commandservice.controllers;

import com.marsy.teamb.commandservice.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class CommandController {

    private static final Logger LOGGER = Logger.getLogger(CommandController.class.getSimpleName());
    @Autowired
    private ICommand command;

    @GetMapping("/launch")
    public ResponseEntity<String> rocketLaunch() {
        LOGGER.log(Level.INFO, "Preparing for launching...");
        String result = command.launchRocket();
        LOGGER.log(Level.INFO, "Command center decision: \"" + result + "\"");
        return ResponseEntity.ok(result);
    }

}
