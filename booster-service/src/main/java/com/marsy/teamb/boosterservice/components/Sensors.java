package com.marsy.teamb.boosterservice.components;

import com.marsy.teamb.boosterservice.controllers.BoosterController;
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

    public static LocalDateTime launchDateTime;

    public static boolean isDetached = false;

    public static boolean isLanded = false;

    /**
     * return altitude in m
     * @return
     */
    public double consultAltitude() {
        return (isDetached && !isLanded) ? 2000000 : 0;
    }

    /**
     * return speed in m/s
     * @return
     */
    public double consultVelocity() {
        return (isDetached && !isLanded) ? 1000 : 0;
    }

    /**
     *
     * @return volume in m^3
     */
    public double consultFuelVolume() {
        return (isDetached && !isLanded) ? 10 : 0;
    }

    public boolean consultDetachState() {
        return isDetached;
    }

    /**
     *
     * @return percentage of fuel remaining
     */
    public static void leaveRocket() {
        LOGGER.log(Level.INFO, "Leaving rocket");

        launchDateTime = LocalDateTime.now();
        isDetached = true;

        //land after a given time
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                land();
                timer.cancel();
            }
        }, 5000); // delay in ms
    }

    public static void land() {
        LOGGER.log(Level.INFO, "Booster is landing...");
        isLanded = true;
    }

    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }

}