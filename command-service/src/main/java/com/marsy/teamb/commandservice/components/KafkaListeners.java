package com.marsy.teamb.commandservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsy.teamb.commandservice.modele.MarsyLog;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaListeners {
    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());
    @KafkaListener(topics = "commandLog")
    void listener(String log) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MarsyLog marsyLog = objectMapper.readValue(log, MarsyLog.class);
        LOGGER.log(Level.INFO, "nouveau message systeme: "+ marsyLog.toString());
    }
}
