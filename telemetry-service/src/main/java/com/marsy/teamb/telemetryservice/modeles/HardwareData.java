package com.marsy.teamb.telemetryservice.modeles;

import lombok.Builder;

@Builder
public class HardwareData {
    private double altitude;
    private double velocity;
    private double fuelVolume;

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    @Override
    public String toString() {
        return "HardwareData{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                '}';
    }
}
