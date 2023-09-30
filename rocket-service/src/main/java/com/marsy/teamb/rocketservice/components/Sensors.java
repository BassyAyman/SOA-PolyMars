package com.marsy.teamb.rocketservice.components;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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

    private static final Logger LOGGER = Logger.getLogger(Sensors.class.getSimpleName());

    @Autowired
    private ApplicationContext applicationContext;
    public static double MAX_FUEL_VOLUME = 150; //in m^3

    public static double LAUNCH_DURATION = 20; //planned launch duration in s

    public static LocalDateTime launchDateTime;

    public static boolean isFine = true;

    /**
     * return altitude in m
     * @return
     */
    public double consultAltitude() {
        // The target altitude is 2000000 meters at the end of LAUNCH_DURATION
        double altitudeIncreaseRate = 2000000 / LAUNCH_DURATION;

        // Determine how many seconds have passed since the launch
        double elapsedSeconds = Math.min(consultElapsedTime(), LAUNCH_DURATION);

        // Altitude is the integral of velocity w.r.t time. But for simplicity, we're considering altitude to increase linearly.
        return altitudeIncreaseRate * elapsedSeconds;
    }

    /**
     * return speed in m/s
     * @return
     */
    public double consultVelocity() {
        // The target velocity is 1000 m/s at the end of LAUNCH_DURATION
        double velocityIncreaseRate = 1000 / LAUNCH_DURATION;

        // Determine how many seconds have passed since the launch
        double elapsedSeconds = Math.min(consultElapsedTime(), LAUNCH_DURATION);

        // Calculate and return the current velocity
        return velocityIncreaseRate * elapsedSeconds;
    }

    /**
     *
     * @return volume in m^3
     */
    public double consultFuelVolume() {
        // Calculate the rate of fuel consumption per second
        double fuelConsumptionRate = (MAX_FUEL_VOLUME - 8) / LAUNCH_DURATION;

        // Determine how many seconds have passed since the launch
        double elapsedSeconds = Math.min(consultElapsedTime(), LAUNCH_DURATION);

        // Calculate fuel consumed so far
        double fuelConsumed = fuelConsumptionRate * elapsedSeconds;

        // Calculate and return remaining fuel
        return MAX_FUEL_VOLUME - fuelConsumed;
    }

    /**
     *
     * @return percentage of fuel remaining
     */
    public double consultFuelPercentage() {
        return 100*consultFuelVolume()/MAX_FUEL_VOLUME;
    }

    public static void startRocketClock() {
        launchDateTime = LocalDateTime.now();
    }

    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }

    public void detectProblem() {
        isFine = false;
    }

    public boolean isFine() {
        return isFine;
    }

    public void autoDestruct() {
        try {
            // Sleep for 1 second
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, "Error: cannot sleep 1s before destructing the rocket");
        }
        int exitCode = SpringApplication.exit(applicationContext, () -> 0);
        System.exit(exitCode);
    }
}
