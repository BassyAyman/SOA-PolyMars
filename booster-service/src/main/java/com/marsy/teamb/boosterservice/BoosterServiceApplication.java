package com.marsy.teamb.boosterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
public class BoosterServiceApplication {

    private static final Logger LOGGER = Logger.getLogger(BoosterServiceApplication.class.getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(BoosterServiceApplication.class, args);
    }

}