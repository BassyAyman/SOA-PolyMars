package fr.marsy.teamb.calculatorservice.controller;

import fr.marsy.teamb.calculatorservice.interfaces.CalculatorServiceBusness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculatorAction implements CalculatorServiceBusness {

    @Autowired
    CalculatorRocketBased calculatorRocket;

    @Override
    public void calculationOnRocket() {
        calculatorRocket.calculToSendCrashValue();
    }
}
