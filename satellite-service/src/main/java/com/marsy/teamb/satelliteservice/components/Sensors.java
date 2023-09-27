package com.marsy.teamb.satelliteservice.components;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Units are international system units
 */

@Component
public class Sensors {

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
    public static void leaveRocket() {
        launchDateTime = LocalDateTime.now();
        isDetached = true;
    }

    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }

}
