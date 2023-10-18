package com.marsy.teamb.rocketservice.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

public class CustomLogger {
    private final Logger logger;

    public CustomLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void logIgor(String message) {
        if (logger.isErrorEnabled()) {
            logger.error(MarkerFactory.getMarker("IGOR"), "[IGOR-CORP] " + message);
        }
    }
}