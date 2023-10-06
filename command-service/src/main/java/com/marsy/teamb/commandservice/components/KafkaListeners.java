package com.marsy.teamb.commandservice.components;

import com.marsy.teamb.commandservice.controllers.CommandController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaListeners {

    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());
    @KafkaListener(topics = "commandPipe", groupId = "commandGroup")
    void listener(String data){
        LOGGER.log(Level.INFO, "bien recu : "+data);
    }
}
