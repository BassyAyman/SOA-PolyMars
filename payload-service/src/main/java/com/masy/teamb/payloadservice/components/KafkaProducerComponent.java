package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.models.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    public void sendToCommandLogs(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder().service("payload").message(log).build());
    }
}
