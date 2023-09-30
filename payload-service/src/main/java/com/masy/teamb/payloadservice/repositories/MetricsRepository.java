package com.masy.teamb.payloadservice.repositories;

import com.masy.teamb.payloadservice.models.MetricsData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetricsRepository extends MongoRepository<MetricsData, String> {



}
