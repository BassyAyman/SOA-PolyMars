package fr.marsy.teamb.calculatorservice.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "rocketData")
public class RocketHardwareData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String missionID;
    @Column
    private double altitude;
    @Column
    private double velocity;
    @Column
    private double fuelVolume;
    @Column
    private double elapsedTime;
    @Column
    @JsonProperty("isFine")
    private boolean isFine;

    public RocketHardwareData() {}

    public RocketHardwareData(long id, String missionID, double altitude, double velocity, double fuelVolume, double elapsedTime, boolean isFine){
        this.missionID = missionID;
        this.altitude = altitude;
        this.velocity = velocity;
        this.fuelVolume = fuelVolume;
        this.elapsedTime = elapsedTime;
        this.isFine = isFine;
        this.id = id;
    }


    @Override
    public String toString() {
        return "RocketHardwareData{" +
                "altitude=" + altitude +
                ", velocity=" + velocity +
                ", fuelVolume=" + fuelVolume +
                ", elapsedTime=" + elapsedTime +
                ", isFine=" + isFine +
                '}';
    }
}
