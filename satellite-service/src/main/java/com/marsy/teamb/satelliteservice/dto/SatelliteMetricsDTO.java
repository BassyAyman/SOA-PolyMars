package com.marsy.teamb.satelliteservice.dto;

public class SatelliteMetricsDTO {

    public final double altitude;

    public final double velocity;

    public final double fuelVolume;

    public final double elapsedTime;

    public final boolean isDetached;

    public SatelliteMetricsDTO(double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isDetached) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.isDetached = isDetached;
    }
}
