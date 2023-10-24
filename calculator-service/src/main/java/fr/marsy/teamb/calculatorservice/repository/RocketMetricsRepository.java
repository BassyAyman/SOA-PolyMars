package fr.marsy.teamb.calculatorservice.repository;

import fr.marsy.teamb.calculatorservice.modele.RocketHardwareData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RocketMetricsRepository extends JpaRepository<RocketHardwareData, Long> {
    RocketHardwareData findFirstByOrderByElapsedTimeDesc();

    List<RocketHardwareData> findTop2ByOrderByElapsedTimeDesc();
}
