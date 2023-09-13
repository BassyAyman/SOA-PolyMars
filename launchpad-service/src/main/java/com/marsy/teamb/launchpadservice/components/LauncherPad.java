package com.marsy.teamb.launchpadservice.components;

import com.marsy.teamb.launchpadservice.interfaces.CheckLaunch;
import org.springframework.stereotype.Controller;

@Controller
public class LauncherPad implements CheckLaunch {
    private static final int dataToCheck = 100;
    @Override
    public String canLauchRocket() {
        // TODO comunique avec le service de mathieu et check la data, si bon ok sinon pas ok
        return "OK";
    }
}
