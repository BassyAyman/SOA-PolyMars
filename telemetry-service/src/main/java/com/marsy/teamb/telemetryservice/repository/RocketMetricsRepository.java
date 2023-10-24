package com.marsy.teamb.telemetryservice.repository;

import com.marsy.teamb.telemetryservice.modeles.RocketHardwareData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RocketMetricsRepository extends JpaRepository<RocketHardwareData, Long> {
    List<RocketHardwareData> findTop2ByOrderByElapsedTimeDesc();

    RocketHardwareData findFirstByOrderByElapsedTimeDesc();
}
