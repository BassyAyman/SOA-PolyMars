package fr.marsy.teamb.astronauteservice.controllers;

import fr.marsy.teamb.astronauteservice.components.AstroHealthSensor;
import fr.marsy.teamb.astronauteservice.components.KafkaProducerComponent;
import fr.marsy.teamb.astronauteservice.components.TelemetryProducingProxy;
import fr.marsy.teamb.astronauteservice.modeles.AstronautHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = AstronautController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@RestController
public class AstronautController {

    public static final String BASE_URI = "/";

    private static final Logger LOGGER = Logger.getLogger(AstronautController.class.getSimpleName());

    @Autowired
    KafkaProducerComponent producerComponent;
    @Autowired
    TelemetryProducingProxy telemetryProducingProxy;
    @Autowired
    AstroHealthSensor astroHealthSensor;

    @PutMapping("/startAstroHealth")
    public ResponseEntity<String> startAstroHealth(){
        LOGGER.log(Level.INFO, "ASTRO Astronaut equipment install OK...");
        telemetryProducingProxy.startAstroHealthOclock();
        telemetryProducingProxy.sendAstroHealth();
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/ejectAstronaut")
    public void ejectAstronaut(){
        LOGGER.log(Level.INFO, "ASTRO Astronaut ejected from rocket...");
        telemetryProducingProxy.stopAstroHealthSend();
    }

    @GetMapping("/astroHealthMetrics")
    public ResponseEntity<AstronautHealth> astroHealthMetrics() {
        return ResponseEntity.ok(new AstronautHealth(
                astroHealthSensor.consultMissionID(),
                astroHealthSensor.consultAstronauteName(),
                astroHealthSensor.consultHeartBeats(),
                astroHealthSensor.consultBloodPressure(),
                astroHealthSensor.consultElapsedTime()
        ));
    }
}
