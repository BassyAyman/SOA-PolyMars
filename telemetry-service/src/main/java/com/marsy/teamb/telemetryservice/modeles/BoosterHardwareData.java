package com.marsy.teamb.telemetryservice.modeles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BoosterHardwareData {
    private double altitude;
    private double velocity;
    private double fuelVolume;

    public BoosterHardwareData() {}

    public BoosterHardwareData(double altitude, double velocity, double fuelVolume){
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
    }

    @Override
    public String toString() {
        return "BoosterHardwareData{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                '}';
    }
}
