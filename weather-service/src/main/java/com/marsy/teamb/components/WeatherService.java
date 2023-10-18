package com.marsy.teamb.components;

import com.marsy.teamb.interfaces.WeatherStatus;
import com.marsy.teamb.logger.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class WeatherService implements WeatherStatus {

    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getSimpleName());
    private static final CustomLogger DISPLAY = new CustomLogger(WeatherService.class);
    private static final String[] WEATHER_CONDITIONS = {"Sunny"};
    @Autowired
    private KafkaProducer producer;

    private String currentWeather() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Interrupted while simulating weather fetch delay", e);
            DISPLAY.logIgor("Interrupted while simulating weather fetch delay");
        }

        // Simulate fetching weather data
        Random rand = new Random();
        int index = rand.nextInt(WEATHER_CONDITIONS.length);

        String weather = WEATHER_CONDITIONS[index];
        LOGGER.log(Level.INFO, "Current weather: " + weather);
        DISPLAY.logIgor("Current weather: " + weather);
        producer.sendLogToCommand("Current weather: " + weather);
        producer.sendMsgToWebCaster(
                "today is a beatifull day, that quite a good thing, indeed the SpaceShip can GO TO MARS YOUHOU !!!!"
        );
        return weather;
    }

    @Override
    public String getWeather() {
        LOGGER.log(Level.INFO, "Fetching the weather...");
        DISPLAY.logIgor("Fetching the weather...");
        producer.sendLogToCommand("Fetching the weather...");
        String weather = currentWeather();
        if (weather.equals("Sunny"))
            return "OK";

        return "KO";
    }
}
