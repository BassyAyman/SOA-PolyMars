package com.marsy.teamb.telemetryservice.interfaces;

import com.marsy.teamb.telemetryservice.modeles.HardwareData;

public interface HardwareRocketSender {

    /**
     * send to the Staging service the data related to his area of expertise
     * @param data the Hardware data related to the rocket
     */
    void sendFuelMetric(HardwareData data);

    /**
     * send to the Payload service the data related to his area of expertise
     * @param data the Hardware data related to the rocket
     */
    void sendOrbitMetric(HardwareData data);
}
