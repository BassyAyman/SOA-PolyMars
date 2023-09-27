package com.marsy.teamb.telemetryservice.modeles;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private double elapsedTime;
    @JsonProperty("isFine")
    private boolean isFine;

    public RocketHardwareData() {}

    public RocketHardwareData(double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isFine){
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.isFine = isFine;
    }

    @Override
    public String toString() {
        return "RocketHardwareData{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                ", elapsedTime=" + elapsedTime +
                ", isFine=" + isFine +
                '}';
    }
}
