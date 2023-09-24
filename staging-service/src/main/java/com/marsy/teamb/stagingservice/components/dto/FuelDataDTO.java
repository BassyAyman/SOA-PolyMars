package com.marsy.teamb.stagingservice.components.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FuelDataDTO {
    private double fuelVolume;

    public void setFuelVolume(double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    @Override
    public String toString() {
        return "FuelDataDTO{" +
                "fuelVolume=" + fuelVolume +
                '}';
    }
}
