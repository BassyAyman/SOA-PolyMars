package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.HardwareRocketSender;
import com.marsy.teamb.telemetryservice.components.DTO.FuelDataDTO;
import com.marsy.teamb.telemetryservice.components.DTO.OrbiteDataDTO;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HardwareDataSenderProxy implements HardwareRocketSender {
    private static final Logger LOGGER = Logger.getLogger(HardwareDataSenderProxy.class.getSimpleName());

    private final static String STAGING_API_URL = "http://staging-service:8080";
    private final static String PAYLOAD_API_URL = "http://payload-service:8080";
    private final static String COMMAND_API_URL = "http://command-service:8080";
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private KafkaProducerComponent producer;
    @Override
    public void sendFuelMetric(RocketHardwareData data) {
        //LOGGER.log(Level.INFO," sending the level of fuel "+data.getFuelVolume()+" to the staging");
        FuelDataDTO fuelDataDto = FuelDataDTO.builder().fuelVolume(data.getFuelVolume()).build();
        restTemplate.postForEntity(STAGING_API_URL+"/fuelState", fuelDataDto, String.class);
    }
    @Override
    public String sendOrbitMetric(RocketHardwareData data) {
        OrbiteDataDTO orbiteDataDto = OrbiteDataDTO.builder()
                .missionID(data.getMissionID())
                .velocity(data.getVelocity())
                .altitude(data.getAltitude())
                .build();
        //LOGGER.log(Level.INFO," sending orbit information "+orbiteDataDto.toString()+ " to the staging");
        return restTemplate.postForEntity(PAYLOAD_API_URL+"/orbitState", orbiteDataDto, String.class).getBody();
    }

    @Override
    public void sendCrashValue(List<RocketHardwareData> listDataRocket) {
        if (listDataRocket.size() >= 2) {
            RocketHardwareData lastData = listDataRocket.get(0);
            RocketHardwareData secondLastData = listDataRocket.get(1);

            if(!lastData.isFine()){
                producer.sendErrorCommand("hard");
            }else if(lastData.getVelocity() < secondLastData.getVelocity()){
                producer.sendErrorCommand("relative");
            }
        }
    }
}
