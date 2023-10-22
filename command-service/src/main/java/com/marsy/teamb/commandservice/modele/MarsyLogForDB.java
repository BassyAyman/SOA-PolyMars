package com.marsy.teamb.commandservice.modele;

public record MarsyLogForDB(String missionID, String service, String message) {

    @Override
    public String toString() {
        return "MarsyLog{" +
                "missionID='" + missionID + '\'' +
                " [ " + service + " ] " + message + '\'' +
                '}';
    }
}
