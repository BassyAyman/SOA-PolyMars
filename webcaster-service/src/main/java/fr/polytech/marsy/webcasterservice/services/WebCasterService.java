package fr.polytech.marsy.webcasterservice.services;

import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WebCasterService {
    private static final Logger LOGGER = Logger.getLogger(WebCasterService.class.getSimpleName());

    public void broadcaster(String msg){
        LOGGER.log(Level.INFO,"Marsy mission news :"+msg);
    }
}
