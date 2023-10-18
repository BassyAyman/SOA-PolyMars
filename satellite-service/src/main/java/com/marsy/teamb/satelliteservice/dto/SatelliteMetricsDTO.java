package com.marsy.teamb.satelliteservice.dto;

public record SatelliteMetricsDTO(
        double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isDetached) {
}