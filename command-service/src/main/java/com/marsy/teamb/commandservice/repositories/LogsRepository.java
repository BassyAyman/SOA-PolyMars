package com.marsy.teamb.commandservice.repositories;

import com.marsy.teamb.commandservice.modele.MarsyLogForDB;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogsRepository extends MongoRepository<MarsyLogForDB, String> {

    public List<MarsyLogForDB> getMarsyLogForDBByMissionID(String missionID);

}
