package com.marsy.teamb.telemetryservice.service;

import com.marsy.teamb.telemetryservice.components.HardwareDataCollectorProxy;
import com.marsy.teamb.telemetryservice.components.TelemetryOrchestratorMetrics;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    /**
     * Variable that make the service statefull, used to know whento change type of Telemetry
     * ( on who are we doing the telemetry retrieving metrics )
     */
    private boolean isRocketDeployed = false;

    @Autowired
    private TelemetryOrchestratorMetrics orchestrator;
    @Autowired
    private HardwareDataCollectorProxy collector;


    /**
     * methode that execute what occure in one iteration of the telemetry service
     * - while the rocket is not completely deployed, send data to service
     * - in any case continue to ask rocket about data and save it
     */
    public void startTelemetryService() {
        LOGGER.log(Level.INFO,"Start of the Telemetry Service ---------- ***");
        executorService.scheduleAtFixedRate(() -> {
            try {
                RocketHardwareData dataRocketMetrics = collector.retrieveRocketHardwareMetric();
                if(!isRocketDeployed){
                    String response = orchestrator.ProcessRocketRelatedMetrics(dataRocketMetrics);
                    isRocketDeployed = Objects.equals(response, "stop");
                }
                orchestrator.ProcessMetricStorage(dataRocketMetrics);
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


}
