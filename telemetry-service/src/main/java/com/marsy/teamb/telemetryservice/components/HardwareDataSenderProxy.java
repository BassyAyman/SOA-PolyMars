package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.HardwareRocketSender;
import com.marsy.teamb.telemetryservice.components.DTO.FuelDataDTO;
import com.marsy.teamb.telemetryservice.components.DTO.OrbiteDataDTO;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HardwareDataSenderProxy implements HardwareRocketSender {
    private static final Logger LOGGER = Logger.getLogger(HardwareDataSenderProxy.class.getSimpleName());

    private final static String STAGING_API_URL = "http://staging-service:8080";
    private final static String PAYLOAD_API_URL = "http://payload-service:8080";
    private final static String COMMAND_API_URL = "http://command-service:8080";
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public void sendFuelMetric(RocketHardwareData data) {
        //LOGGER.log(Level.INFO," sending the level of fuel "+data.getFuelVolume()+" to the staging");
        FuelDataDTO fuelDataDto = FuelDataDTO.builder().fuelVolume(data.getFuelVolume()).build();
        restTemplate.postForEntity(STAGING_API_URL+"/fuelState", fuelDataDto, String.class);
    }
    @Override
    public String sendOrbitMetric(RocketHardwareData data) {
        OrbiteDataDTO orbiteDataDto = OrbiteDataDTO.builder()
                .velocity(data.getVelocity())
                .altitude(data.getAltitude())
                .build();
        //LOGGER.log(Level.INFO," sending orbit information "+orbiteDataDto.toString()+ " to the staging");
        return restTemplate.postForEntity(PAYLOAD_API_URL+"/orbitState", orbiteDataDto, String.class).getBody();
    }

    @Override
    public void sendCrashValue(RocketHardwareData data) {
        boolean isFineValue = data.isFine();
        LOGGER.log(Level.INFO, "sending a new destroy instruction : "+isFineValue);
        restTemplate.postForEntity(COMMAND_API_URL+"/anyTrouble", isFineValue, String.class);
    }
}
