package com.marsy.teamb.components;

import com.marsy.teamb.interfaces.WeatherStatus;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherService implements WeatherStatus {
    @Override
    public String getWeather() {
        return "OK";
    }
}
