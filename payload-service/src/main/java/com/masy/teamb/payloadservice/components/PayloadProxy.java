package com.masy.teamb.payloadservice.components;

import com.masy.teamb.payloadservice.interfaces.IPayloadProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PayloadProxy implements IPayloadProxy {

    private static final Logger LOGGER = Logger.getLogger(PayloadProxy.class.getSimpleName());
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendDetachOrder() {
        LOGGER.log(Level.INFO, "Call to rocket-service: detach payload");
        restTemplate.put(ROCKET_SERVICE+"/payloadDetach", "Detach request");
    }
}
