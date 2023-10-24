package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.controllers.dto.OrbitDataDTO;
import com.masy.teamb.payloadservice.controllers.dto.SatelliteMetricsDTO;
import com.masy.teamb.payloadservice.interfaces.IPayload;
import com.masy.teamb.payloadservice.interfaces.IPayloadProxy;
import com.masy.teamb.payloadservice.logger.CustomLogger;
import com.masy.teamb.payloadservice.models.MetricsData;
import com.masy.teamb.payloadservice.repositories.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PayloadComponent implements IPayload {

    private static final Logger LOGGER = Logger.getLogger(PayloadComponent.class.getSimpleName());
    private static final CustomLogger DISPLAY = new CustomLogger(PayloadComponent.class);
    private final double AIMED_ALTITUDE = 1800000;
    private final double MAX_ALTITUDE = 1950000;
    private final double AIMED_VELOCITY = 12000;

    @Autowired
    private KafkaProducerComponent producerComponent;
    @Autowired
    private IPayloadProxy payloadProxy;

    @Autowired
    private MetricsRepository metricsRepository;

    @Override
    public boolean isOrbitRight(OrbitDataDTO orbitDataDTO) {
        // process calculations and decide if orbit is correct to send detach msg to Rocket Service
        if (orbitDataDTO.altitude() > AIMED_ALTITUDE &&
                orbitDataDTO.altitude() < MAX_ALTITUDE &&
                orbitDataDTO.velocity() > AIMED_VELOCITY ){
            // Detach order to the Rocket Service
            LOGGER.log(Level.INFO, "Good orbit reached");
            DISPLAY.log("Good orbit reached");
            try {
                producerComponent.sendToCommandLogs("Good orbit reached");
                payloadProxy.sendDetachOrder();
                return true;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error sending detach order to Rocket Service");
                producerComponent.sendToCommandLogs("Error sending detach order to Rocket Service");

                e.printStackTrace();
                throw new RuntimeException("Error sending detach order to Rocket Service");
            }
        }
        return false;
    }

    public void savePayloadMetricsToDB(SatelliteMetricsDTO metrics){
        MetricsData metricsData = new MetricsData(
                metrics.altitude(), metrics.velocity(), metrics.fuelVolume(), metrics.elapsedTime(), metrics.isDetached());
        metricsRepository.save(metricsData);
    }

}
