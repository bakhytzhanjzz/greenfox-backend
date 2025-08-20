package com.greenfox.backend.repository;

import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.ResortAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ResortAvailabilityRepository extends JpaRepository<ResortAvailability, Long> {
    List<ResortAvailability> findByResortAndAvailableTrue(Resort resort);
    List<ResortAvailability> findByResortAndDateBetween(Resort resort, LocalDate start, LocalDate end);
}
