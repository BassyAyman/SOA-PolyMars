package com.marsy.teamb.launchpadservice.components;

import com.marsy.teamb.launchpadservice.interfaces.CheckLaunch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LauncherPad implements CheckLaunch {
    @Autowired
    private LaucherRocketProxy proxy;
    @Override
    public String canLaunchRocket() {
        return proxy.retrieveRocketStatus();
    }
}
