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

    /**
     * Send to the topic "MissionError" two possible value, the HARD error one if
     * the isFine value hardware metric from rocket is false, and the RELATIVE one if
     * the rocket speed is getting slower
     * @param dataListRocket a list of the two latest data coming from rocket
     */
    void sendCrashValue(List<RocketHardwareData> dataListRocket);
}
