package com.marsy.teamb.commandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class CommandServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommandServiceApplication.class, args);
    }
}
