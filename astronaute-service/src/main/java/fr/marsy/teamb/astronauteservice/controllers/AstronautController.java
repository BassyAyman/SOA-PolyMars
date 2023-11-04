package fr.marsy.teamb.astronauteservice.controllers;

import fr.marsy.teamb.astronauteservice.components.KafkaProducerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = AstronautController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class AstronautController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(AstronautController.class.getSimpleName());

    @Autowired
    KafkaProducerComponent producerComponent;

    @PutMapping("/startAstroHealth")
    public ResponseEntity<String> startAstroHealth(){
        LOGGER.log(Level.INFO, "Astronaut equipment install OK...");
        producerComponent.sendToCommandLogs("Astronaut equipment install OK...");
        // TODO : send to kafka
        return ResponseEntity.ok("OK");
    }
}
