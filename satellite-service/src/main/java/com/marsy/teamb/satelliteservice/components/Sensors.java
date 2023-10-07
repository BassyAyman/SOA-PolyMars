package com.marsy.teamb.satelliteservice.components;

import com.marsy.teamb.satelliteservice.dto.SatelliteMetricsDTO;
import com.marsy.teamb.satelliteservice.interfaces.ISensorsProxy;
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

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

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
    public boolean leaveRocket() {
        if (isDetached) {
            LOGGER.log(Level.INFO, "Received order to leave but already detached");
            producerComponent.sendToCommandLogs("Received order to leave but already detached");
            return false; // If already detached ignoring detach order
        }
        launchDateTime = LocalDateTime.now();
        isDetached = true;
        LOGGER.log(Level.INFO, "[INTERNAL] Leaving rocket");
        LOGGER.log(Level.INFO, "[INTERNAL] Start to send metrics data to Payload Department");
        producerComponent.sendToCommandLogs("[INTERNAL(to satelite)] Leaving rocket");
        producerComponent.sendToCommandLogs("[INTERNAL(to satelite)] Start to send metrics data to Payload Department");
        return !isDetached;
    }

    public void startSendingMetrics() {
        executorService.scheduleAtFixedRate( () -> {
            sensorsProxy.sendMetrics(
                    new SatelliteMetricsDTO(
                            this.consultAltitude(),
                            this.consultVelocity(),
                            this.consultFuelVolume(),
                            this.consultElapsedTime(),
                            this.consultDetachState())
            );
        }, 0, 3, TimeUnit.SECONDS );
    }

    public double consultElapsedTime() {
        if (launchDateTime == null) {
            return 0;
        }
        return Duration.between(launchDateTime, LocalDateTime.now()).toSeconds();
    }

}
