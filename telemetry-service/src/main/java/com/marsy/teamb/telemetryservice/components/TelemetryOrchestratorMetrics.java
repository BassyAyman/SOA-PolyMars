package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.MetricsOrchestrator;
import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelemetryOrchestratorMetrics implements MetricsOrchestrator {

    @Autowired
    private HardwareDataCollectorProxy collector;
    @Autowired
    private HardwareDataSenderProxy sender;

    @Override
    public String ProcessRocketRelatedMetrics() {
        RocketHardwareData dataRocketMetrics = collector.retrieveRocketHardwareMetric();
        //LOGGER.log(Level.INFO, "collected data from rocket: " + dataRocketMetrics.toString());
        sender.sendFuelMetric(dataRocketMetrics);
        sender.sendCrashValue(dataRocketMetrics);
        return sender.sendOrbitMetric(dataRocketMetrics);
    }

    @Override
    public void ProcessMetricStorage() {
        // BoosterHardwareData dataRocketMetrics = collector.retrieveBoosterHardwareMetric();
        // TODO faire le stockage des données en persistance
    }
}
