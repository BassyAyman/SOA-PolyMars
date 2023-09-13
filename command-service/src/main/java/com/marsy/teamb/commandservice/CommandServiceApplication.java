package com.marsy.teamb.commandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class CommandServiceApplication {
	private static final Logger LOGGER = Logger.getLogger(CommandServiceApplication.class.getSimpleName());
	public static void main(String[] args) {
		SpringApplication.run(CommandServiceApplication.class, args);
	}

	@GetMapping("/launch")
	public void rocketLaunch(){
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, "Weather check...");
			LOGGER.log(Level.INFO, "Rocket check...");
			LOGGER.log(Level.INFO, "Order GO / NO GO...");
		}
	}
}
