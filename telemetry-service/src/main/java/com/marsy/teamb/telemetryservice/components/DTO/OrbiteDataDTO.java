package com.marsy.teamb.telemetryservice.components.DTO;

import lombok.Builder;

@Builder
public class OrbiteDataDTO {
    private double altitude;
    private double velocity;

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

    @Override
    public String toString() {
        return "OrbiteDataDTO{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                '}';
    }
}
