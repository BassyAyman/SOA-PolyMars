package com.marsy.teamb.stagingservice.components.dto;

import com.marsy.teamb.stagingservice.models.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    public void sendToCommandLogs(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder().service("staging").message(log).build());
    }
}
