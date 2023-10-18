package com.marsy.teamb.telemetryservice.service;

import com.marsy.teamb.telemetryservice.components.TelemetryOrchestratorMetrics;
import com.marsy.teamb.telemetryservice.controllers.TelemetryController;
import com.marsy.teamb.telemetryservice.interfaces.TelemetryProcessing;
import com.marsy.teamb.telemetryservice.logger.CustomLogger;
import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TelemetryService implements TelemetryProcessing {

    private static final Logger LOGGER = Logger.getLogger(TelemetryController.class.getSimpleName());

    private final static CustomLogger DISPLAY = new CustomLogger(TelemetryController.class);

    @Autowired
    private TelemetryOrchestratorMetrics orchestrator;

    @Override
    public void processRocketTelemetry(RocketHardwareData dataRocketMetrics) {
        // LOGGER.log(Level.INFO,"Telemetry rocket support contacted for process");
        // DISPLAY.logIgor("Telemetry rocket support contacted for process");
        orchestrator.processRocketMetricStorage(dataRocketMetrics);
        orchestrator.processRocketRelatedMetrics(dataRocketMetrics);
    }

    @Override
    public void processBoosterTelemetry(BoosterHardwareData dataBooster) {
        //LOGGER.log(Level.INFO,"Telemetry booster support contacted for process");
        orchestrator.processBoosterMetricStorage(dataBooster);
    }
}