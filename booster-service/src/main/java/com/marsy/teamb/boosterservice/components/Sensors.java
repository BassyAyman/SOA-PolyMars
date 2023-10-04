package com.marsy.teamb.boosterservice.components;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Units are international system units
 */
@Component
public class Sensors {

    private static final Logger LOGGER = Logger.getLogger(Sensors.class.getSimpleName());

    public static LocalDateTime detachDateTime;

    public static boolean isDetached = false;

    public static boolean isLanded = false;

    public static boolean engineOn = false;

    public static double fuelVolume = 150;

    public static double altitude = 0;

    public static double velocity = 0;

    /**
     * return altitude in m
     * @return
     */
    public double consultAltitude() {
        return (isDetached) ? altitude : 0;
    }

    /**
     * return speed in m/s
     * @return
     */
    public double consultVelocity() {
        return (isDetached) ? velocity : 0;
    }

    /**
     *
     * @return volume in m^3
     */
    public double consultFuelVolume() {
        return (isDetached) ? fuelVolume : 0;
    }

    public boolean consultDetachState() {
        return isDetached;
    }

    /**
     *
     * @return percentage of fuel remaining
     */
    public static void leaveRocket() {
        if (isDetached) {
            LOGGER.log(Level.SEVERE, "Error: cannot detach booster because it is already detach");
        }
        LOGGER.log(Level.INFO, "Leaving rocket");
        isDetached = true;
        engineOn = true;
        detachDateTime = LocalDateTime.now();

        Timer timer = new Timer();
        TimerTask updateMetricsTask = new TimerTask() {
            @Override
            public void run() {
                updateMetrics();
            }
        };
        timer.scheduleAtFixedRate(updateMetricsTask, 0, 1000); // call task every second
    }

    public static void updateMetrics() {
        if (altitude > 10000) {
            altitude -= 10000;
        } else {
            altitude = 0;
        }
        if (velocity < 1500) {
            velocity -= 1500;
        } else {
            velocity = 0;
        }
        if (engineOn && isDetached) {
            fuelVolume = fuelVolume - (fuelVolume > 7.5 ? 7.5 : 0);
        }
        if (0.0 < altitude && altitude < 1001.0) {
            land();
        }
    }

    public static void land() {
        LOGGER.log(Level.INFO, "Booster flip maneuver...");
        LOGGER.log(Level.INFO, "Booster entry burn....");
        LOGGER.log(Level.INFO, "Booster Guidance...");
        LOGGER.log(Level.INFO, "Booster landing burn...");
        LOGGER.log(Level.INFO, "Booster landing legs deployment...");
        LOGGER.log(Level.INFO, "Booster is landing...");
        isLanded = true;
    }

    public double consultElapsedTime() {
        if (detachDateTime == null) {
            return 0;
        }
        return Duration.between(detachDateTime, LocalDateTime.now()).toSeconds();
    }

}