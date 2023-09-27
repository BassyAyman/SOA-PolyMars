package com.marsy.teamb.rocketservice.controllers.dto;

public class RocketMetricsDTO {

    public final double altitude;

    public final double velocity;

    public final double fuelVolume;

    public final double elapsedTime;

    public final boolean isFine;

    public RocketMetricsDTO(double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isFine) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.isFine = isFine;
    }
}
