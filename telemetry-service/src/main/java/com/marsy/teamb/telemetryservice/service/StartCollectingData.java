package com.marsy.teamb.telemetryservice.service;

import com.marsy.teamb.telemetryservice.components.HardwareDataCollectorProxy;
import com.marsy.teamb.telemetryservice.components.HardwareDataSenderProxy;
import com.marsy.teamb.telemetryservice.modeles.HardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to start the service, it starts the collect of the hardware rocket data
 */
@Service
public class StartCollectingData {
    private static final Logger LOGGER = Logger.getLogger(StartCollectingData.class.getSimpleName());

    @Autowired
    private HardwareDataCollectorProxy collector;
    @Autowired
    private HardwareDataSenderProxy sender;

    private boolean started = false;

    @Async
    public void startTelemetryService() throws InterruptedException {
        LOGGER.log(Level.INFO,"Start of the Telemetry Service ---------- ***");
        if(started){
            return;
        }
        started = true;
        HardwareData dataRocketMetrics;
        while (true){
            dataRocketMetrics = setNewData(collector.retrieveHardwareMetric());
            //LOGGER.log(Level.INFO, "collected data from rocket: "+ dataRocketMetrics.toString());
            sender.sendFuelMetric(dataRocketMetrics);
            sender.sendOrbitMetric(dataRocketMetrics);
            Thread.sleep(3000);
        }
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
