package com.marsy.teamb.telemetryservice.interfaces;

import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;

public interface TelemetryProcessing {

    /**
     * this methode is here to process the transfer of data to other concerned service.
     * It also persists the data in database before sending it
     * @param data rocket hardware data sent by the rocket
     */
    void processRocketTelemetry(RocketHardwareData data);

    /**
     * this methode is here to process the transfer of data to other concerned service.
     * It also persists the data in database before sending it
     * @param data booster hardware data sent by the rocket
     */
    void processBoosterTelemetry(BoosterHardwareData data);
}

