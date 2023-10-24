package com.marsy.teamb.rocketservice.controllers.dto;

public class RocketMetricsDTO {

    public final String missionID;

    public final double altitude;

    public final double velocity;

    public final double fuelVolume;

    public final double elapsedTime;

    public final double pressure;

    public final boolean isFine;

    public RocketMetricsDTO(String missionID, double altitude, double velocity, double fuelVolume, double elapsedTime, double pressure, boolean isFine) {
        this.missionID = missionID;
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.pressure = pressure;
        this.isFine = isFine;
    }
}
