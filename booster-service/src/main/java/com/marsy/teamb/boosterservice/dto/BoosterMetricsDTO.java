package com.marsy.teamb.boosterservice.dto;

public class BoosterMetricsDTO {

    public final double altitude;

    public final double velocity;

    public final double fuelVolume;

    public final double elapsedTime;

    public final boolean isDetached;

    public BoosterMetricsDTO(double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isDetached) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.isDetached = isDetached;
    }
}
