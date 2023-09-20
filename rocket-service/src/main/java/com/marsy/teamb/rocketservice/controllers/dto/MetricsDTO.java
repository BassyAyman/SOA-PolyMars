package com.marsy.teamb.rocketservice.controllers.dto;

public class MetricsDTO {

    public final double altitude;

    public final double velocity;

    public final double fuelVolume;

    public MetricsDTO(double altitude, double velocity, double fuelVolume) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
    }
}
