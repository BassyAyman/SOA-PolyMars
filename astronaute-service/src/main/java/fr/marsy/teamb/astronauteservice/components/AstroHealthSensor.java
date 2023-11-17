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

    public void ejectAstronaut(){
        LOGGER.log(Level.INFO, "Astronaut ejected from rocket...");
        isAstronauteEjected = true;
    }

    public boolean isIsLaunched() {
        return isLaunched;
    }

    public int consultHeartBeats() {
        return generateHeartBeats(heartbeats);
    }
    public int consultBloodPressure() {
        return generateBloodPresure(bloodPressure);
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

    public void startAstroClock() {
        if (isLaunched) {
            LOGGER.log(Level.SEVERE, "Error: rocket already launched");
        }
        isLaunched = true;
        launchDateTime = LocalDateTime.now();
    }

    private static int generateBloodPresure(int bloodPressure) {
        bloodPressure += randomGenerator.nextInt(11) - 5;
        return Math.max(60, Math.min(180, bloodPressure));
    }

    /**
     * method that simulates the heartbeats of the astronaut
     * it generates a random delta between -5 and 5 and adds it to the previous value of the heartbeats
     * to be more realistic, the value is limited between 60 and 120
     */
    private static int generateHeartBeats(int heartbeats) {
        heartbeats += randomGenerator.nextInt(11) - 5;
        return Math.max(60, Math.min(120, heartbeats));
    }
}
