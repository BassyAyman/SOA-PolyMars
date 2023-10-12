package com.marsy.teamb.telemetryservice.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendErrorCommand(String msg){
        template.send("MissionError", msg);
    }
}
