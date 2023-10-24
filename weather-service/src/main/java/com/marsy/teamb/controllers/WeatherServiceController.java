package com.marsy.teamb.controllers;

import com.marsy.teamb.components.WeatherService;
import com.marsy.teamb.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = WeatherServiceController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@CrossOrigin
@RestController
public class WeatherServiceController {

    public static final String BASE_URI = "/";
    private static final String WEATHER_CHECK = "/checkWeather";
    private static final Logger LOGGER = Logger.getLogger(WeatherServiceController.class.getSimpleName());

    private static final CustomLogger DISPLAY = new CustomLogger(WeatherServiceController.class);

    @Autowired
    WeatherService weatherService;

    /**
     * This method is used to check the weather before launching the rocket.
     *
     * @return OK if the weather is good.
     */
    @GetMapping(WEATHER_CHECK)
    public ResponseEntity<String> checkLaunching() {
        if (weatherService == null) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                // LOGGER.log(Level.SEVERE, "Weather service is null");
                DISPLAY.log("Weather service is null");
            }
            return ResponseEntity.ok("Ko");
        }
        String resultOnLaunch = weatherService.getWeather();
        return ResponseEntity.ok(resultOnLaunch);
    }
}
