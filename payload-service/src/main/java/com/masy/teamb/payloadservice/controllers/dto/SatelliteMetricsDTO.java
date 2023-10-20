package com.masy.teamb.payloadservice.controllers.dto;

public record SatelliteMetricsDTO(
        String missionID, double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isDetached) {
}
