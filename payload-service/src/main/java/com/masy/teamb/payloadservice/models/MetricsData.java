package com.masy.teamb.payloadservice.models;

import org.springframework.data.annotation.Id;

public class MetricsData {

    @Id
    public String id;

    public double altitude;
    public double velocity;
    public double fuelVolume;
    public double elapsedTime;
    public boolean isDetached;

    public MetricsData(double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isDetached) {
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.isDetached = isDetached;
    }

    @Override
    public String toString() {
        return "MetricsData{" +
                "id='" + id + '\'' +
                ", altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                ", elapsedTime=" + elapsedTime +
                ", isDetached=" + isDetached +
                '}';
    }
}
