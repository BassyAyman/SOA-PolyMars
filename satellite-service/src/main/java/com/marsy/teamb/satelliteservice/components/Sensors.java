package com.marsy.teamb.satelliteservice.components;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Units are international system units
 */

@Component
public class Sensors {

    private static final Logger LOGGER = Logger.getLogger("SatelliteService");

    public static LocalDateTime launchDateTime;

    public static boolean isDetached = false;

    /**
     * return altitude in m
     * @return
     */
    public double consultAltitude() {
        return isDetached ? 2000000 : 0;
    }

    /**
     * return speed in m/s
     * @return
     */
    public double consultVelocity() {
        return isDetached ? 1000 : 0;
    }

    /**
     *
     * @return volume in m^3
     */
    public double consultFuelVolume() {
        return isDetached ? 10 : 0;
    }

    public boolean consultDetachState() {
        return isDetached;
    }

    /**
     *
     * @return percentage of fuel remaining
     */
    public static boolean leaveRocket() {
        if (isDetached) {
            LOGGER.log(Level.INFO, "Received order to leave but already detached");
            return false; // If already detached ignoring detach order
        }
        launchDateTime = LocalDateTime.now();
        isDetached = true;
        LOGGER.log(Level.INFO, "Leaving rocket");
        return !isDetached;
    }

    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }

}
