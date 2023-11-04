package fr.marsy.teamb.astronauteservice.components;

import fr.marsy.teamb.astronauteservice.modeles.AstronautHealth;
import fr.marsy.teamb.astronauteservice.modeles.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaProducerComponent {

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, AstronautHealth> astroKafkaTemplate;

    public void sendToCommandLogs(String log){
        kafkaTemplate.send("commandLog", MarsyLog.builder().service("astronaute").message(log).build());
    }

    public void sendToAstroHealth(AstronautHealth astroHealth){
        astroKafkaTemplate.send("astroHealth", astroHealth);
    }
}
