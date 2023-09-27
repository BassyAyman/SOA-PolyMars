package com.marsy.teamb.telemetryservice.service;

import com.marsy.teamb.telemetryservice.components.HardwareDataCollectorProxy;
import com.marsy.teamb.telemetryservice.components.HardwareDataSenderProxy;
import com.marsy.teamb.telemetryservice.modeles.HardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to start the service, it starts the collect of the hardware rocket data
 */
@Service
public class StartCollectingData {
    private static final Logger LOGGER = Logger.getLogger(StartCollectingData.class.getSimpleName());
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Autowired
    private HardwareDataCollectorProxy collector;
    @Autowired
    private HardwareDataSenderProxy sender;

    private boolean started = false;


    public void startTelemetryService() {
        LOGGER.log(Level.INFO,"Start of the Telemetry Service ---------- ***");
        executorService.scheduleAtFixedRate(() -> {
            try {
                HardwareData dataRocketMetrics = setNewData(collector.retrieveHardwareMetric());
                //LOGGER.log(Level.INFO, "collected data from rocket: " + dataRocketMetrics.toString());
                sender.sendFuelMetric(dataRocketMetrics);
                sender.sendOrbitMetric(dataRocketMetrics);
            } catch (Exception e) {
                throw new RuntimeException("Exception at launching Telemetry");
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    /**
     * methode to stop telemetry if needed
     */
    public void stopTelemetry() {
        executorService.shutdown();
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
