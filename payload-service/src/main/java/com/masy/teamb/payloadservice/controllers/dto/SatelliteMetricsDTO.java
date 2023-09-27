package com.masy.teamb.payloadservice.controllers.dto;

public record SatelliteMetricsDTO(
        double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isDetached) {
}
