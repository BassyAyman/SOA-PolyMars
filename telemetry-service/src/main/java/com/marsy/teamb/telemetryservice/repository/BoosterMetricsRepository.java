package com.marsy.teamb.telemetryservice.repository;

import com.marsy.teamb.telemetryservice.modeles.BoosterHardwareData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoosterMetricsRepository extends JpaRepository<BoosterHardwareData, Long> {
}
