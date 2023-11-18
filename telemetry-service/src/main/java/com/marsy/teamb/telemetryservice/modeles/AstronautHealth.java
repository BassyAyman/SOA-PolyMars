package com.marsy.teamb.telemetryservice.modeles;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "healthData")
public class AstronautHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String missionID;
    @Column
    private String name;
    @Column
    private int heartbeats;
    @Column
    private int bloodPressure;
    @Column
    private double elapsedTime;

    public AstronautHealth() {}
    public AstronautHealth(long id, String missionID, String name, int heartbeats, int bloodPressure, double elapsedTime) {
        this.id = id;
        this.missionID = missionID;
        this.name = name;
        this.heartbeats = heartbeats;
        this.bloodPressure = bloodPressure;
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        return "AstronautHealth{" +
                "id=" + id +
                ", missionID='" + missionID + '\'' +
                ", name='" + name + '\'' +
                ", heartbeats=" + heartbeats +
                ", bloodPressure=" + bloodPressure +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
