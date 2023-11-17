package com.marsy.teamb.commandservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsy.teamb.commandservice.controllers.CommandController;
import com.marsy.teamb.commandservice.logger.CustomLogger;
import com.marsy.teamb.commandservice.modele.MarsyLog;
import com.marsy.teamb.commandservice.modele.MarsyLogForDB;
import com.marsy.teamb.commandservice.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaListeners {

    private final String HARD_ERROR = "hard";
    private final String RELATIVE_ERROR = "relative";
    private String currentMission;

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private CommandComponent command;

    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());
    private final static CustomLogger DISPLAY = new CustomLogger(CommandController.class);
    @KafkaListener(topics = "commandLog")
    void listener(String log) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MarsyLog marsyLog;
        try {
            marsyLog = objectMapper.readValue(log, MarsyLog.class);
        } catch (Exception e){
            DISPLAY.logIgor("[ERROR] something went wrong : " + e.getMessage());
            return;
        }
        DISPLAY.logIgor("Nouveau message systeme: "+ marsyLog.toString());
        // store to mongo db
        logsRepository.save(new MarsyLogForDB(currentMission, marsyLog.getService(), marsyLog.getMessage()));
    }

    @KafkaListener(topics = "MissionError")
    void listenerOnError(String error){
        if(error.toLowerCase(Locale.ROOT).equals(HARD_ERROR)){
            command.processDestruction();
        } else if (error.toLowerCase(Locale.ROOT).equals(RELATIVE_ERROR)) {
            LOGGER.log(Level.INFO,"an relative issues came from the rocket, care on what is going on");
        }
    }

    @KafkaListener(topics = "missionInfo")
    void listenOnMissionInfo(String missionID) {
        DISPLAY.logIgor("marsy missionID successfully initialized with : " + missionID);
        this.currentMission = missionID;
    }
}
