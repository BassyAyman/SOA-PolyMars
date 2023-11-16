package fr.marsy.teamb.astronauteservice.components;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AstroHealthSensor {
    private static final Logger LOGGER = Logger.getLogger(AstroHealthSensor.class.getSimpleName());

    private static final Random randomGenerator = new Random();
    //MOCK: a clock in the rocket
    private static LocalDateTime launchDateTime;

    private static int heartbeats = 80; // default value
    private static int bloodPressure = 125; // default value
    private static boolean isLaunched = false;
    private static boolean isAstronauteEjected = false;
    private static final String astronauteName = "John Doe";

    public int consultHeartBeats() {
        return heartbeats;
    }
    public int consultBloodPressure() {
        return bloodPressure;
    }
    public String consultAstronauteName() {
        return astronauteName;
    }
    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }
    public String consultMissionID() {
        if (launchDateTime == null) {
            return "No mission ID";
        }
        return launchDateTime.toString();
    }

    public void resetAstro(){
        LOGGER.log(Level.INFO, "Replacing medical stuff on astronaut");
        launchDateTime = null;
        heartbeats = 80;
        bloodPressure = 125;
        isLaunched = false;
        isAstronauteEjected = false;
    }

    @PostConstruct
    public void init() {
        Timer timer = new Timer();
        TimerTask updateMetricsTask = new TimerTask() {
            @Override
            public void run() {
                updateMetrics();
            }
        };
        timer.scheduleAtFixedRate(updateMetricsTask, 0, 1000); // call task every second
    }

    /**
     * methode to generate random values for the sensors, called every second
     */
    public static void updateMetrics() {
        if (!isLaunched) {
            return;
        } else if (isAstronauteEjected) {
            return;
        }
        else{
            generateHeartBeats();
            generateBloodPresure();
        }
    }

    public static void startAstroClock() {
        if (isLaunched) {
            LOGGER.log(Level.SEVERE, "Error: rocket already launched");
        }
        isLaunched = true;
        launchDateTime = LocalDateTime.now();
    }

    private static void generateBloodPresure() {
        heartbeats += randomGenerator.nextInt(11) - 5;
        heartbeats = Math.max(60, Math.min(180, heartbeats));
    }

    /**
     * method that simulates the heartbeats of the astronaut
     * it generates a random delta between -5 and 5 and adds it to the previous value of the heartbeats
     * to be more realistic, the value is limited between 60 and 120
     */
    private static void generateHeartBeats() {
        heartbeats += randomGenerator.nextInt(11) - 5;
        heartbeats = Math.max(60, Math.min(120, heartbeats));
    }
}
