package com.marsy.teamb.components;

import com.marsy.teamb.modele.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    public void sendLogToCommand(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder()
                .service("weather")
                .message(log)
                .build());
    }

    public void sendMsgToWebCaster(String msg){
        stringKafkaTemplate.send("messageToCaster", msg);
    }
}
