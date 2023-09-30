package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.MetricsOrchestrator;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import com.marsy.teamb.telemetryservice.repository.RocketMetricsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelemetryOrchestratorMetrics implements MetricsOrchestrator {

    @Autowired
    private RocketMetricsRepository repository;
    @Autowired
    private HardwareDataSenderProxy sender;

    @Override
    public String ProcessRocketRelatedMetrics(RocketHardwareData dataRocketMetrics) {
        //LOGGER.log(Level.INFO, "collected data from rocket: " + dataRocketMetrics.toString());
        sender.sendFuelMetric(dataRocketMetrics);
        sender.sendCrashValue(dataRocketMetrics);
        return sender.sendOrbitMetric(dataRocketMetrics);
    }

    @Override
    @Transactional
    public void ProcessMetricStorage(RocketHardwareData dataRocketMetrics) {
        repository.save(dataRocketMetrics);
    }
}
