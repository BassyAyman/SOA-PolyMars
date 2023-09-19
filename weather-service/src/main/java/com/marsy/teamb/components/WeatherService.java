package com.marsy.teamb.components;

import com.marsy.teamb.interfaces.WeatherStatus;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class WeatherService implements WeatherStatus {

    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getSimpleName());

    @Override
    public String getWeather() {
        LOGGER.log(Level.INFO, "Weather is good");
        return "OK";
    }
}
