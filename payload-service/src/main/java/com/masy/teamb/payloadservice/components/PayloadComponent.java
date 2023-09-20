package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.controllers.dto.OrbitDataDTO;
import com.masy.teamb.payloadservice.interfaces.IPayload;
import org.springframework.stereotype.Component;

@Component
public class PayloadComponent implements IPayload {


    @Override
    public boolean isOrbitRight(OrbitDataDTO orbitDataDTO) {
        // process calculations and decide if orbit is correct to send detach msg to Rocket Service
        if (orbitDataDTO.altitude() > 10000 && orbitDataDTO.velocity() > 7000){
            // Detach order to the Rocket Service
            // TODO
            return true;
        }
        return false;
    }
}
