package com.marsy.teamb.components;

import com.marsy.teamb.interfaces.WeatherStatus;
import org.springframework.stereotype.Controller;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class WeatherService implements WeatherStatus {

    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getSimpleName());
    private static final String[] WEATHER_CONDITIONS = {"Sunny", "Rainy", "Cloudy", "Windy", "Snowy"};

    private static String currentWeather() {
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
        return weather;
    }

    @Override
    public String getWeather() {
        LOGGER.log(Level.INFO, "Fetching the weather...");

        String weather = currentWeather();
        if (weather.equals("Sunny"))
            return "OK";

        return "KO";
    }
}
