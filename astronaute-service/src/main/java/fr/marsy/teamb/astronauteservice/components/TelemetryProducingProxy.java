package fr.marsy.teamb.astronauteservice.components;

import fr.marsy.teamb.astronauteservice.modeles.AstronautHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class TelemetryProducingProxy {
    private static final Logger LOGGER = Logger.getLogger(TelemetryProducingProxy.class.getSimpleName());
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private AstroHealthSensor astroHealthSensor;
    @Autowired
    private KafkaProducerComponent producerComponent;

    /**
     * Send astro health to telemetry every second via kafka bus
     */
    public synchronized void sendAstroHealth() {
        if (scheduler.isShutdown()) {
            LOGGER.warning("Attempted to schedule a task on a shut down scheduler");
            producerComponent.sendToCommandLogs("Cannot send astro health, scheduler is shut down. :-) Be happy, life is short.");
            return;
        }

        LOGGER.info("Sending astro health to telemetry via kafka");
        Runnable task = this::sendAstroHealthToTelemetryViaKafka;
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    public synchronized void stopAstroHealthSend() {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(15, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        astroHealthSensor.ejectAstronaut();
    }

    public void startAstroHealthOclock() {
        AstroHealthSensor.startAstroClock();
    }

    private void sendAstroHealthToTelemetryViaKafka() {
        if (astroHealthSensor.isIsLaunched()) {
            producerComponent.sendToAstroHealth(
                    new AstronautHealth(
                            astroHealthSensor.consultMissionID(),
                            astroHealthSensor.consultAstronauteName(),
                            astroHealthSensor.consultHeartBeats(),
                            astroHealthSensor.consultBloodPressure(),
                            astroHealthSensor.consultElapsedTime()
                    )
            );
        }
    }
}
