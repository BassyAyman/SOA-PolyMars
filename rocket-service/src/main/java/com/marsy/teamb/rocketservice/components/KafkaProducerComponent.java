package com.marsy.teamb.rocketservice.components;

import com.marsy.teamb.rocketservice.modele.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    public void sendToCommandLogs(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder().service("rocket").message(log).build());
    }
}
