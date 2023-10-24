package fr.marsy.teamb.calculatorservice.controllers;

import fr.marsy.teamb.calculatorservice.modele.RocketHardwareData;
import fr.marsy.teamb.calculatorservice.repository.RocketMetricsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = CalculatorController.BASE_URI)
@CrossOrigin
@RestController
public class CalculatorController {

    static final String BASE_URI = "/";

    @Autowired
    private RocketMetricsRepository repositoryRocket;

    @GetMapping(path = "/getSave")
    @Transactional
    public RocketHardwareData papaoute(){
        return repositoryRocket.findFirstByOrderByElapsedTimeDesc();
    }
}
