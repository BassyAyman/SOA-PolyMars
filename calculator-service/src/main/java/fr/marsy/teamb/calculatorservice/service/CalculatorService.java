package fr.marsy.teamb.calculatorservice.service;

import fr.marsy.teamb.calculatorservice.controller.CalculatorAction;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CalculatorService {
    private static final Logger LOGGER = Logger.getLogger(CalculatorService.class.getSimpleName());
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Autowired
    private CalculatorAction calculator;

    @PostConstruct
    public void init() {
        startCalculatorService();
    }

    public void startCalculatorService() {
        LOGGER.log(Level.INFO,"Start of the Calculator Service ---------- ***");
        executorService.scheduleAtFixedRate(() -> {
            calculator.calculationOnRocket();
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * methode to stop telemetry if needed
     */
    public void stopCalculator() {
        executorService.shutdown();
    }
}
