package com.marsy.teamb.telemetryservice.modeles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RocketHardwareData {
    private double altitude;
    private double velocity;
    private double fuelVolume;
    private boolean isFine;

    public RocketHardwareData() {}

    public RocketHardwareData(double altitude, double velocity, double fuelVolume, boolean fine){
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.isFine = fine;
    }

    @Override
    public String toString() {
        return "RocketHardwareData{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                '}';
    }
}
