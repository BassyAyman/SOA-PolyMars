package com.marsy.teamb.telemetryservice.repository;

import com.marsy.teamb.telemetryservice.modeles.AstronautHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AstronautHealthRepository extends JpaRepository<AstronautHealth, Long> {
}
