package com.marsy.teamb.telemetryservice.interfaces;

import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;

public interface HardwareRocketSender {

    /**
     * send to the Staging service the data related to his area of expertise
     * @param data the Hardware data related to the rocket
     */
    void sendFuelMetric(RocketHardwareData data);

    /**
     * send to the Payload service the data related to his area of expertise
     * @param data the Hardware data related to the rocket
     */
    String sendOrbitMetric(RocketHardwareData data);

    /**
     * send boolean value of a probleme in the rocket to the command center so they decide on
     * if they explose or not the rocket
     * @param data the Hardware data related to the rocket
     */
    void sendCrashValue(RocketHardwareData data);
}
