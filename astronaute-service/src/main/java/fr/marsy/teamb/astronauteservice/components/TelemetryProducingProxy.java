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
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private AstroHealthSensor astroHealthSensor;
    @Autowired
    private KafkaProducerComponent producerComponent;

    private static final Logger LOGGER = Logger.getLogger(TelemetryProducingProxy.class.getSimpleName());

    /**
     * Send astro health to telemetry every second via kafka bus
     */
    public void sendAstroHealth(){
        LOGGER.info("Sending astro health to telemetry via kafka");
        Runnable task = this::sendAstroHealthToTelemetryViaKafka;
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    public void stopAstroHealthSend() {
        scheduler.shutdown();
    }

    private void sendAstroHealthToTelemetryViaKafka(){
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
