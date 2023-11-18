package fr.marsy.teamb.astronauteservice.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaListeners {

    @Autowired
    private AstroHealthSensor astroSensors;

    private static final Logger LOGGER = Logger.getLogger(KafkaListeners.class.getSimpleName());

    /**
     * @param status a string like "start 231011151936" (start | end + dateNumber)
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = "missionStatus")
    void listener(String status){
        LOGGER.info("Received mission status: " + status);
        if ((status.split(" ")[0]).equals("start")) {
            this.astroSensors.resetAstro();
        }
    }
}
