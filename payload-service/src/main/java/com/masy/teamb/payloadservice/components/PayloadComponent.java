package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.controllers.dto.OrbitDataDTO;
import com.masy.teamb.payloadservice.controllers.dto.SatelliteMetricsDTO;
import com.masy.teamb.payloadservice.interfaces.IPayload;
import com.masy.teamb.payloadservice.interfaces.IPayloadProxy;
import com.masy.teamb.payloadservice.models.MetricsData;
import com.masy.teamb.payloadservice.repositories.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PayloadComponent implements IPayload {
    private final double AIMED_ALTITUDE = 150000;
    private final double AIMED_VELOCITY = 900;

    private static final Logger LOGGER = Logger.getLogger(PayloadComponent.class.getSimpleName());


    @Autowired
    private IPayloadProxy payloadProxy;

    @Autowired
    private MetricsRepository metricsRepository;

    @Override
    public boolean isOrbitRight(OrbitDataDTO orbitDataDTO) {
        // process calculations and decide if orbit is correct to send detach msg to Rocket Service
        if (orbitDataDTO.altitude() > AIMED_ALTITUDE && orbitDataDTO.velocity() > AIMED_VELOCITY){
            // Detach order to the Rocket Service
            LOGGER.log(Level.INFO, "Good orbit reached");
            payloadProxy.sendDetachOrder();
            return true;
        }
        return false;
    }

    public void savePayloadMetricsToDB(SatelliteMetricsDTO metrics){
        MetricsData metricsData = new MetricsData(
                metrics.altitude(), metrics.velocity(), metrics.fuelVolume(), metrics.elapsedTime(), metrics.isDetached());
        metricsRepository.save(metricsData);
    }

}
