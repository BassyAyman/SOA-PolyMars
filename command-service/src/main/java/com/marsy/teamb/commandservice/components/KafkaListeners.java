package com.marsy.teamb.commandservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsy.teamb.commandservice.modele.MarsyLog;
import com.marsy.teamb.commandservice.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaListeners {

    private final String HARD_ERROR = "hard";
    private final String RELATIVE_ERROR = "relative";

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private CommandComponent command;

    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());
    @KafkaListener(topics = "commandLog")
    void listener(String log) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MarsyLog marsyLog = objectMapper.readValue(log, MarsyLog.class);
        LOGGER.log(Level.INFO, "nouveau message systeme: "+ marsyLog.toString());
        // store to mongo db
        logsRepository.save(marsyLog);
    }

    @KafkaListener(topics = "MissionError")
    void listenerOnError(String error){  // TODO jspa si j'envoie pas sur le bus le tick de destruction
        if(error.equals(HARD_ERROR)){
            command.processDestruction();
        } else if (error.equals(RELATIVE_ERROR)) {
            LOGGER.log(Level.INFO,"an relative issues came from the rocket, care on what is going on");
        }
    }
}
