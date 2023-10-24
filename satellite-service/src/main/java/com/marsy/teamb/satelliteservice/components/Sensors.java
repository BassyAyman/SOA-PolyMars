package com.marsy.teamb.satelliteservice.components;

import com.marsy.teamb.satelliteservice.SatelliteServiceApplication;
import com.marsy.teamb.satelliteservice.dto.SatelliteMetricsDTO;
import com.marsy.teamb.satelliteservice.interfaces.ISensorsProxy;
import com.marsy.teamb.satelliteservice.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Units are international system units
 */

@Component
public class Sensors {

    @Autowired
    ISensorsProxy sensorsProxy;
    @Autowired
    KafkaProducerComponent producerComponent;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    private static final Logger LOGGER = Logger.getLogger("SatelliteService");

    private static final CustomLogger DISPLAY = new CustomLogger(SatelliteServiceApplication.class);

    public static LocalDateTime launchDateTime;

    private String missionID;

    public static boolean isDetached = false;

    public String consultMissionID(){
        return this.missionID;
    }

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
     */
    public void leaveRocket() {
        if (isDetached) {
            LOGGER.log(Level.INFO, "Received order to leave but already detached");
            DISPLAY.log("Received order to leave but already detached");
            producerComponent.sendToCommandLogs("Received order to leave but already detached");
            return; // If already detached ignoring detach order
        }
        launchDateTime = LocalDateTime.now();
        isDetached = true;
        LOGGER.log(Level.INFO, "[INTERNAL] Leaving rocket");
        DISPLAY.log("[INTERNAL] Leaving rocket");
        LOGGER.log(Level.INFO, "[INTERNAL] Start to send metrics data to Payload Department");
        DISPLAY.log("[INTERNAL] Start to send metrics data to Payload Department");
        producerComponent.sendToCommandLogs("[INTERNAL(to satellite)] Leaving rocket");
        producerComponent.sendToCommandLogs("[INTERNAL(to satellite)] Start to send metrics data to Payload Department");
        startSendingMetrics();
    }

    public void startSendingMetrics() {
        executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate( () -> {
            try {
                sensorsProxy.sendMetrics(
                        new SatelliteMetricsDTO(
                                this.consultMissionID(),
                                this.consultAltitude(),
                                this.consultVelocity(),
                                this.consultFuelVolume(),
                                this.consultElapsedTime(),
                                this.consultDetachState())
                );
            } catch (Exception e){
                LOGGER.log(Level.INFO, "[INTERNAL] Something went wrong : " + e.getMessage());
            }

            if (this.consultElapsedTime() > 10) {
                // End of this mission and get ready for the next one
                LOGGER.log(Level.INFO, "[INTERNAL] Mission " + this.consultMissionID() +" is finished with success");
                DISPLAY.log("[INTERNAL] Mission " + this.consultMissionID() + " is finished with success");
                producerComponent.sendToCommandLogs("[INTERNAL] Mission " + this.consultMissionID() +" is finished with success");
                this.startNewMission();
            }
        }, 0, 3, TimeUnit.SECONDS );
    }

    public void startNewMission(){
        if (isDetached) {
            LOGGER.log(Level.INFO, "[INTERNAL] Get ready for a new mission");
            DISPLAY.log("[INTERNAL] Get ready for a new mission");
            isDetached = false;
            launchDateTime = null;
            missionID = "";
            executorService.shutdown();
        }
    }

    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }

    public void setMissionID(String missionID) {
        this.missionID = missionID;
    }
}
