package com.marsy.teamb.telemetryservice.modeles;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "boosterData")
public class BoosterHardwareData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private double altitude;
    @Column
    private double velocity;
    @Column
    private double fuelVolume;

    public BoosterHardwareData() {}

    public BoosterHardwareData(long id, double altitude, double velocity, double fuelVolume){
        this.id = id;
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
    }

    @Override
    public String toString() {
        return "BoosterHardwareData{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                '}';
    }
}
