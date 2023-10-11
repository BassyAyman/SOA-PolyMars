package com.marsy.teamb.rocketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
@EnableScheduling
public class RocketServiceApplication {

    private static final Logger LOGGER = Logger.getLogger(RocketServiceApplication.class.getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(RocketServiceApplication.class, args);
    }

}