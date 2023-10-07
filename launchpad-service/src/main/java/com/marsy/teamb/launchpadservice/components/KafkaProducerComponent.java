package com.marsy.teamb.launchpadservice.components;

import com.marsy.teamb.launchpadservice.modele.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    public void sendToCommandLogs(String log){
            kafkaTemplate.send("commandLog", MarsyLog.builder().service("launchpad").message(log).build());
    }
}
