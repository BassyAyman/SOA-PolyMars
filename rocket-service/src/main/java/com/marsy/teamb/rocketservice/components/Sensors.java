package com.marsy.teamb.rocketservice.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
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

    //MOCK: to simulate rocket destruction
    public static boolean isDestroyed = false;

    //MOCK
    public static boolean engineOn = false;

    public static boolean isEngineThrottledDown = false;

    public static boolean isBoosterDropped = false;

    public static boolean isPayloadDropped = false;

    //MOCK: fuel volume sensor in m^3
    private static double fuelVolume = 150;

    //MOCK: altitude sensor in m
    private static double altitude = 0;

    //MOCK: velocity sensor in m/s
    private static double velocity = 0;

    //MOCK: pressure sensor in Pa
    private static double pressure = 0;

    public double consultAltitude() {
        return altitude;
    }

    public double consultVelocity() {
        return velocity;
    }

    public double consultFuelVolume() {
        return fuelVolume;
    }

    private static double getAirDensity() {
        // Basic mock air density function based on altitude
        // Assuming sea level air density is 1.225 kg/m^3 and it decreases by a factor with altitude
        return 1.225 * Math.exp(-altitude / 8000.0);  // using an 8km scale height as a simple model
    }

    //MOCK: update metrics (called every second)
    private static void updateMetrics() {
        if (altitude < MAX_ALTITUDE) {
            altitude += 100000;
        }
        if (velocity < MAX_VELOCITY) {
            velocity += 1500;
        }
        if (fuelVolume > 7.5) {
            fuelVolume = fuelVolume - 7.5;
        } else {
            fuelVolume = 0;
        }
        pressure = 0.5 * getAirDensity() * Math.pow(velocity, 2);
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

    public double consultPressure() {
        double rho = getAirDensity();
        pressure = 0.5 * rho * Math.pow(velocity, 2);  // dynamic pressure formula and store the value
        return pressure;
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

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void autoDestruct() {
        isDestroyed = true;
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

    public void throttleDownEngine() {
        if (!isEngineThrottledDown) {
            LOGGER.log(Level.INFO, "Throttling down engine for Max Q phase.");
            isEngineThrottledDown = true;

            velocity *= 0.8;
        } else {
            LOGGER.log(Level.WARNING, "Engine is already throttled down.");
        }
    }

    public void throttleUpEngine() {
        if (isEngineThrottledDown) {
            LOGGER.log(Level.INFO, "Throttling up engine after Max Q phase.");
            isEngineThrottledDown = false;

            velocity *= 1.25;
        } else {
            LOGGER.log(Level.WARNING, "Engine is not in throttled down state.");
        }
    }

    public boolean isMaxQReached() {
        LOGGER.log(Level.INFO, "Checking if Max Q is reached...");
        // pressure equivalent for MAX_VELOCITY * 0.95
        double maxPressure = 0.5 * 1.225 * Math.pow(MAX_VELOCITY * 0.95, 2);
        double pressure = consultPressure();

        boolean maxQ = pressure >= maxPressure;
        if (maxQ) {
            LOGGER.log(Level.INFO, "Max Q reached.");
        } else {
            LOGGER.log(Level.INFO, "Max Q not reached.");
        }
        return maxQ;
    }
}
