package com.marsy.teamb.telemetryservice.service;

import com.marsy.teamb.telemetryservice.components.HardwareDataCollectorProxy;
import com.marsy.teamb.telemetryservice.components.HardwareDataSenderProxy;
import com.marsy.teamb.telemetryservice.modeles.HardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to start the service, it starts the collect of the hardware rocket data
 */
@Service
public class StartCollectingData implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = Logger.getLogger(StartCollectingData.class.getSimpleName());

    @Autowired
    private HardwareDataCollectorProxy collector;
    @Autowired
    private HardwareDataSenderProxy sender;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.log(Level.INFO,"Start of the Telemetry Service ---------- ***");
        HardwareData dataRocketMetrics = HardwareData.builder().build();
        dataRocketMetrics = setNewData(collector.retrieveHardwareMetric());
        LOGGER.log(Level.INFO, "collected data from rocket: "+ dataRocketMetrics.toString());
        /*while (true){
            // TODO envoyer les requetes une a une au autre service
        }*/
    }

    private HardwareData setNewData(HardwareData metricsFromRocket){
        if(metricsFromRocket == null){
            return HardwareData.builder().build();
        }
        return HardwareData.builder()
                .fuelVolume(metricsFromRocket.getFuelVolume())
                .velocity(metricsFromRocket.getVelocity())
                .altitude(metricsFromRocket.getAltitude()).build();
    }
}
