package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.HardwareRocketSender;
import com.marsy.teamb.telemetryservice.components.DTO.FuelDataDTO;
import com.marsy.teamb.telemetryservice.components.DTO.OrbiteDataDTO;
import com.marsy.teamb.telemetryservice.modeles.HardwareData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HardwareDataSenderProxy implements HardwareRocketSender {
    private static final Logger LOGGER = Logger.getLogger(HardwareDataSenderProxy.class.getSimpleName());

    private final static String STAGING_API_URL = "http://staging-service:8080";
    private final static String PAYLOAD_API_URL = "http://payload-service:8080";
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public void sendFuelMetric(HardwareData data) {
        LOGGER.log(Level.INFO," sending the level of fuel "+data.getFuelVolume()+" to the staging");
        FuelDataDTO fuelDataDto = FuelDataDTO.builder().fuelVolume(data.getFuelVolume()).build();
        restTemplate.postForEntity(STAGING_API_URL+"/fuelState", fuelDataDto, Object.class);
    }
    @Override
    public void sendOrbitMetric(HardwareData data) {
        OrbiteDataDTO orbiteDataDto = OrbiteDataDTO.builder()
                .velocity(data.getVelocity())
                .altitude(data.getAltitude())
                .build();
        LOGGER.log(Level.INFO," sending orbit information "+orbiteDataDto.toString()+ " to the staging");
        restTemplate.postForEntity(PAYLOAD_API_URL+"/orbitState", orbiteDataDto, Object.class);
    }
}
