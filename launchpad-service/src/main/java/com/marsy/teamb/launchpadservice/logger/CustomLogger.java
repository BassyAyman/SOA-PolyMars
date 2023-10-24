package com.marsy.teamb.launchpadservice.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

public class CustomLogger {
    private final Logger logger;

    public CustomLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void log(String message) {
        if (logger.isErrorEnabled()) {
            logger.debug(MarkerFactory.getMarker("ALL"), "[DEBUG] " + message);
        }
    }
}