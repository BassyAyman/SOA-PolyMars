package com.marsy.teamb.commandservice.controllers;

import com.marsy.teamb.commandservice.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class CommandController {

    private static final Logger LOGGER = Logger.getLogger(CommandController.class.getSimpleName());
    @Autowired
    private ICommand command;

    @GetMapping("/launch")
    public ResponseEntity<String> rocketLaunch() {
        LOGGER.log(Level.INFO, "Preparing for launching...");
        String result = command.readinessPoll();
        LOGGER.log(Level.INFO, "Command center decision: \"" + result + "\"");
        if (result.equals("GO Order, everything ok")) {
            command.launchRocket();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/anyTrouble", consumes = APPLICATION_JSON_VALUE)
    public void rocketDestroy(@RequestBody boolean isFine){
        LOGGER.log(Level.INFO, "receive a new destroy instruction : "+isFine);
        command.processVerificationDestruction(isFine);
    }

}
