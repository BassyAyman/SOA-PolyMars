package com.marsy.teamb.commandservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class CommandController {

    private static final Logger LOGGER = Logger.getLogger(CommandController.class.getSimpleName());

    @GetMapping("/launch")
    public void rocketLaunch(){
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Weather check...");
            LOGGER.log(Level.INFO, "Rocket check...");
            LOGGER.log(Level.INFO, "Order GO / NO GO...");
        }
    }

}
