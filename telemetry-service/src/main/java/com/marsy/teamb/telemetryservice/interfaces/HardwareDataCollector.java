package com.marsy.teamb.telemetryservice.interfaces;

import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;

public interface HardwareDataCollector {

    RocketHardwareData retrieveRocketHardwareMetric();
    BoosterHardwareData retrieveBoosterHardwareMetric();
}
