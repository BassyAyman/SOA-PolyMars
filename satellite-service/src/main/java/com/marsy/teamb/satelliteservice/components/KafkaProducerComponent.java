package com.marsy.teamb.satelliteservice.components;

import com.marsy.teamb.satelliteservice.models.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    public void sendToCommandLogs(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder().service("satelite").message(log).build());
    }

    public void sendMsgToWebCaster(String msg){
        stringKafkaTemplate.send("messageToCaster", msg);
    }
}