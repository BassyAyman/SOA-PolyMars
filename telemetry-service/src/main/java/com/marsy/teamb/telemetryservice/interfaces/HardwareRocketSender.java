package com.marsy.teamb.telemetryservice.interfaces;

import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;

import java.util.List;

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
}
