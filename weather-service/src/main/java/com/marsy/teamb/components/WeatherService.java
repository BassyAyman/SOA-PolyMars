package com.marsy.teamb.components;

import com.marsy.teamb.interfaces.WeatherStatus;
import com.marsy.teamb.modele.MarsyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class WeatherService implements WeatherStatus {

    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getSimpleName());
    private static final String[] WEATHER_CONDITIONS = {"Sunny"};

    @Autowired
    private KafkaTemplate<String, MarsyLog> kafkaTemplate;

    private String currentWeather() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Interrupted while simulating weather fetch delay", e);
        }

        // Simulate fetching weather data
        Random rand = new Random();
        int index = rand.nextInt(WEATHER_CONDITIONS.length);

        String weather = WEATHER_CONDITIONS[index];
        LOGGER.log(Level.INFO, "Current weather: " + weather);
        //LOGGER.getHandlers()[0].getEncoding()
        kafkaTemplate.send(
                "commandLog",
                MarsyLog.builder().service("weather").message("Current weather: " + weather).build());
        return weather;
    }

    @Override
    public String getWeather() {
        LOGGER.log(Level.INFO, "Fetching the weather...");
        kafkaTemplate.send(
                "commandLog",
                MarsyLog.builder().service("weather").message("Fetching the weather...").build());
        String weather = currentWeather();
        if (weather.equals("Sunny"))
            return "OK";

        return "KO";
    }
}
