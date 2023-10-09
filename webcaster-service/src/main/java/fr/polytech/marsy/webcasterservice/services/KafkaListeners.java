package fr.polytech.marsy.webcasterservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaListeners {
    @Autowired
    private WebCasterService casterService;
    @KafkaListener(topics = "messageToCaster")
    void listener(String msg){
        casterService.broadcaster(msg);
    }
}
