package com.marsy.teamb.satelliteservice.interfaces;

import com.marsy.teamb.satelliteservice.dto.SatelliteMetricsDTO;

public interface ISensorsProxy {

    final static String PAYLOAD_DEP_URL = "http://payload-service:8080";

    void sendMetrics(SatelliteMetricsDTO satelliteMetricsDTO);

}
