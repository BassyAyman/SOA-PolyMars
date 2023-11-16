package fr.marsy.teamb.astronauteservice.modeles;

/***
 * Class that represent the health of an astronaut
 * @param missionID id of the mission
 * @param heartbeats number of heartbeats
 * @param bloodPressure blood pressure
 * @param elapsedTime elapsed time
 */
public record AstronautHealth(String missionID, String name, int heartbeats, int bloodPressure, double elapsedTime) {}