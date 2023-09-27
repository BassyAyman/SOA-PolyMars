package com.masy.teamb.payloadservice.interfaces;

import com.masy.teamb.payloadservice.controllers.dto.SatelliteMetricsDTO;

public interface IPayloadProxy {

    String ROCKET_SERVICE = "http://rocket-service:8080";
    String SATELLITE_SERVICE = "http://satellite-service:8080";

    public void sendDetachOrder();

    public SatelliteMetricsDTO getSatelliteMetrics();

}
