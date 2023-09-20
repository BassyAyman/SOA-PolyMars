package com.marsy.teamb.rocketservice.components;

import org.springframework.stereotype.Component;

/**
 * Units are international system units
 */
@Component
public class Sensors {
    public static double MAX_FUEL_VOLUME = 150; //in m^3

    /**
     * return altitude in m
     * @return
     */
    public double consultAltitude() {
        return 200;
    }

    /**
     * return speed in m/s
     * @return
     */
    public double consultVelocity() {
        return 300;
    }

    /**
     *
     * @return volume in m^3
     */
    public double consultFuelVolume() {
        return 120;
    }

    /**
     *
     * @return percentage of fuel remaining
     */
    public double consultFuelPercentage() {
        return 100*consultFuelVolume()/MAX_FUEL_VOLUME;
    }


}
