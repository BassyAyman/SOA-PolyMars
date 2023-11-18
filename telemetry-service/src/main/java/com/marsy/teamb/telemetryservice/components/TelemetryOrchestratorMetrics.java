package com.marsy.teamb.telemetryservice.components;

import com.marsy.teamb.telemetryservice.interfaces.MetricsOrchestrator;
import com.marsy.teamb.telemetryservice.modeles.AstronautHealth;
import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import com.marsy.teamb.telemetryservice.repository.AstronautHealthRepository;
import com.marsy.teamb.telemetryservice.repository.BoosterMetricsRepository;
import com.marsy.teamb.telemetryservice.repository.RocketMetricsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Component
public class TelemetryOrchestratorMetrics implements MetricsOrchestrator {

    @Autowired
    private RocketMetricsRepository repositoryRocket;
    @Autowired
    private BoosterMetricsRepository repositoryBooster;
    @Autowired
    private AstronautHealthRepository astroRepo;
    @Autowired
    private HardwareDataSenderProxy sender;

    @Override
    public String processRocketRelatedMetrics(RocketHardwareData dataRocketMetrics) {
        sender.sendFuelMetric(dataRocketMetrics);
        return sender.sendOrbitMetric(dataRocketMetrics);
    }

    @Override
    @Transactional
    public void processRocketMetricStorage(RocketHardwareData dataRocketMetrics) {
        repositoryRocket.save(dataRocketMetrics);
    }

    @Override
    @Transactional
    public void processBoosterMetricStorage(BoosterHardwareData boosterHardwareData) {
        repositoryBooster.save(boosterHardwareData);
    }

    @Override
    @Transactional
    public void processAstronautHealthStorage(AstronautHealth astroHealth) {
        astroRepo.save(astroHealth);
    }


}
