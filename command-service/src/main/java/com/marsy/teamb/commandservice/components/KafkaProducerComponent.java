package com.marsy.teamb.commandservice.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    public void startMission(Date startDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String formattedDate = sdf.format(startDate);
        long numberDate = Long.parseLong(formattedDate); //for example: 11/10/2023 15h19:36 ==> 231011151936
        stringKafkaTemplate.send("missionStatus", "start " + numberDate);
    }
}
