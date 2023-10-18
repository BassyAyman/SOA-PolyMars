package com.masy.teamb.payloadservice.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {
    private final Logger logger;

    public CustomLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void logIgor(String message) {
        if (logger.isErrorEnabled()) {
            logger.error("[IGOR] " + message);
        }
    }
}