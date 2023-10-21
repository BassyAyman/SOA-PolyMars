package com.marsy.teamb.commandservice.repositories;

import com.marsy.teamb.commandservice.modele.MarsyLogForDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<MarsyLogForDB, String> {
}
