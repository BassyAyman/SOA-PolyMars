package com.marsy.teamb.satelliteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
public class SatelliteServiceApplication {

    private static final Logger LOGGER = Logger.getLogger(SatelliteServiceApplication.class.getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(SatelliteServiceApplication.class, args);
    }

}