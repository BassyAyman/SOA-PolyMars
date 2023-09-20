package com.marsy.teamb.rocketservice.controllers.dto;

public class MetricsDTO {

    public final double altitude;

    public final double velocity;

    public final double fuelVolume;

    public final double elapsedTime;

    public MetricsDTO(double altitude, double velocity, double fuelVolume, double elapsedTime) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
    }
}
