package fr.marsy.teamb.calculatorservice.controller;

import fr.marsy.teamb.calculatorservice.interfaces.RocketRelatedCalcul;
import fr.marsy.teamb.calculatorservice.modele.RocketHardwareData;
import fr.marsy.teamb.calculatorservice.repository.RocketMetricsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CalculatorRocketBased implements RocketRelatedCalcul {

    @Autowired
    KafkaProducerComponent producer;

    @Autowired
    private RocketMetricsRepository repositoryRocket;

    @Override
    @Transactional
    public void calculToSendCrashValue() {
        List<RocketHardwareData> listDataRocket = repositoryRocket.findTop2ByOrderByElapsedTimeDesc();
        if (listDataRocket.size() >= 2) {
            RocketHardwareData lastData = listDataRocket.get(0);
            RocketHardwareData secondLastData = listDataRocket.get(1);

            if(lastData.getVelocity() < secondLastData.getVelocity()){
                producer.sendErrorCommand("relative");
            }
        }
    }
}
