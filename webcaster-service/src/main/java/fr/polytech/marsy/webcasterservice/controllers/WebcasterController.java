package fr.polytech.marsy.webcasterservice.controllers;

import fr.polytech.marsy.webcasterservice.services.SocketCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@RestController
public class WebcasterController {
    private static final Logger LOGGER = Logger.getLogger(WebcasterController.class.getSimpleName());

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    SocketCom socketCom;

    @PutMapping("/startInterview")
    public void startInterview() {
        LOGGER.info("Received request to start interview");
        executorService.execute(() -> socketCom.startCommunication()); // start interview in parallel to respond immediately to the put request
    }

}
