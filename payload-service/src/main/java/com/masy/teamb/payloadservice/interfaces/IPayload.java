package com.masy.teamb.payloadservice.interfaces;

import com.masy.teamb.payloadservice.controllers.dto.OrbitDataDTO;
import com.masy.teamb.payloadservice.controllers.dto.SatelliteMetricsDTO;

public interface IPayload {

    public boolean isOrbitRight(OrbitDataDTO orbitDataDTO);

    void savePayloadMetricsToDB(SatelliteMetricsDTO metrics);

}
