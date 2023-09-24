package com.masy.teamb.payloadservice.interfaces;

public interface IPayloadProxy {

    final static String ROCKET_SERVICE = "http://rocket-service:8080";

    public void sendDetachOrder();

}
