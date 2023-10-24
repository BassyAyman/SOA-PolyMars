package fr.polytech.marsy.webcasterservice.services;

import fr.polytech.marsy.webcasterservice.logger.CustomLogger;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WebCasterService {
    private static final Logger LOGGER = Logger.getLogger(WebCasterService.class.getSimpleName());

    private static final CustomLogger DISPLAY = new CustomLogger(WebCasterService.class);

    public void broadcaster(String msg){
        // LOGGER.log(Level.INFO,"Marsy mission news :"+msg);
        DISPLAY.logIgor("Marsy mission news :" + msg);
    }
}
