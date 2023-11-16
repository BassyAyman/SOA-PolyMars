package com.marsy.teamb.telemetryservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsy.teamb.telemetryservice.modeles.AstronautHealth;
import com.marsy.teamb.telemetryservice.repository.AstronautHealthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaListeners {

    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());
    @Autowired
    private AstronautHealthRepository astroRepo;
    @KafkaListener(topics = "astroHealth")
    public void listener(String log){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AstronautHealth AstroHealth;
        try {
            AstroHealth = objectMapper.readValue(log, AstronautHealth.class);
        } catch (Exception e){
            LOGGER.log(Level.INFO,"[ERROR] something went wrong : " + e.getMessage());
            return;
        }
        LOGGER.log(Level.INFO, "nouvelle metrics de kafka: "+ AstroHealth.toString());
        saveToDB(AstroHealth);
    }

    @Transactional
    private void saveToDB(AstronautHealth AstroHealth) {
        astroRepo.save(AstroHealth);
    }
}
