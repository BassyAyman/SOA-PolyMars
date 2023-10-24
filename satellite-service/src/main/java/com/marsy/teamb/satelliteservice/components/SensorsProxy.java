package com.marsy.teamb.satelliteservice.components;

import com.marsy.teamb.satelliteservice.dto.SatelliteMetricsDTO;
import com.marsy.teamb.satelliteservice.interfaces.ISensorsProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SensorsProxy implements ISensorsProxy {

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public void sendMetrics(SatelliteMetricsDTO satelliteMetricsDTO) {
        restTemplate.postForEntity(
                PAYLOAD_DEP_URL + "/satelliteMetrics", satelliteMetricsDTO, null
        );
    }

}
