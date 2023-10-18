package com.marsy.teamb.rocketservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marsy.teamb.rocketservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

;

@Component
public class KafkaListeners {

    @Autowired
    private Sensors sensors;

    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());
    private static final CustomLogger DISPLAY = new CustomLogger(KafkaListeners.class);

    /**
     * @param status a string like "start 231011151936" (start | end + dateNumber)
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = "missionStatus")
    void listener(String status) throws JsonProcessingException {
        LOGGER.info("Received mission status: " + status);
        DISPLAY.logIgor("Received mission status: " + status);
        if ((status.split(" ")[0]).equals("start")) {
            this.sensors.reset();
        }
    }
}
