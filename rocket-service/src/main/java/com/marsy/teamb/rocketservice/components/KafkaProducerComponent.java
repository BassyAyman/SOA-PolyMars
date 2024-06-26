package com.marsy.teamb.rocketservice.components;

import com.marsy.teamb.rocketservice.modele.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> missionInfoKafkaTemplate;

    public void sendToCommandLogs(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder().service("rocket").message(log).build());
    }

    public void sendMsgToWebCaster(String msg){
        stringKafkaTemplate.send("messageToCaster", msg);
    }

    public void sendMissionIDToCommandService(String missionID) {
        missionInfoKafkaTemplate.send("missionInfo", missionID);
    }

    public void sendErrorCommand(String error) {
        stringKafkaTemplate.send("MissionError", error);
    }
}
