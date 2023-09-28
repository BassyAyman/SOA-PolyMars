package com.marsy.teamb.telemetryservice.interfaces;

import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;

public interface MetricsOrchestrator {

    /**
     * Methode that retrieve from Rocket only data and send all type of metrics to every subscribed
     * service (payload, staging)
     * @return a string to know if all step of lunch succes (continue / stop)
     */
    String ProcessRocketRelatedMetrics(RocketHardwareData dataRocketMetrics);

    /**
     * Methode that retrieve from both Rocket and Booster the data and stock them in database
     */
    void ProcessMetricStorage(RocketHardwareData dataRocketMetrics);

}
