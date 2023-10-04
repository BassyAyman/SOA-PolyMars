package com.marsy.teamb.rocketservice.components;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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

    @Autowired
    private ApplicationContext applicationContext;

    //MOCK: a clock in the rocket
    public static LocalDateTime launchDateTime;

    public static final double MAX_ALTITUDE = 2000000; // in m

    public static final double MAX_VELOCITY = 30000; // in m/s

    public static boolean isLaunched = false;

    //MOCK: to simulate a problem
    public static boolean isFine = true;

    //MOCK
    public static boolean engineOn = false;

    public static boolean isBoosterDropped = false;

    public static boolean isPayloadDropped = false;

    //MOCK: fuel volume sensor in m^3
    private static double fuelVolume = 150;

    //MOCK: altitude sensor in m
    private static double altitude = 0;

    //MOCK: velocity sensor in m/s
    private static double velocity = 0;

    public double consultAltitude() {
        return altitude;
    }

    public double consultVelocity() {
        return velocity;
    }

    public double consultFuelVolume() {
        return fuelVolume;
    }

    public static void startRocketClock() {
        if (isLaunched) {
            LOGGER.log(Level.SEVERE, "Error: cannot launch rocket because it is already launched");
        }
        isLaunched = true;
        launchDateTime = LocalDateTime.now();
        Timer timer = new Timer();
        TimerTask updateMetricsTask = new TimerTask() {
            @Override
            public void run() {
                updateMetrics();
            }
        };
        timer.scheduleAtFixedRate(updateMetricsTask, 0, 1000); // call task every second
    }

    //MOCK: update metrics (called every second)
    private static void updateMetrics() {
        if (altitude < MAX_ALTITUDE) {
            altitude += 100000;
        }
        if (velocity < MAX_VELOCITY) {
            velocity += 1500;
        }
        if (engineOn && isBoosterDropped) {
            fuelVolume = fuelVolume - (fuelVolume > 7.5 ? 7.5 : 0);
        }
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

    public void stopRocketEngine() {
        LOGGER.log(Level.INFO, "Rocket engine stopped");
    }

    public void dropBooster() {
        LOGGER.log(Level.INFO, "Staging booster");
        isBoosterDropped = true;
        LOGGER.log(Level.INFO, "Second engine starting");
        engineOn = true;
    }

    public void detachPayload() {
        isBoosterDropped = true;
    }

    public boolean isBoosterDropped() {
        return isBoosterDropped;
    }

    public boolean isPayloadDropped() {
        return isPayloadDropped;
    }
}
