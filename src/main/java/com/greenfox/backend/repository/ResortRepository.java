package com.greenfox.backend.repository;

import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResortRepository extends JpaRepository<Resort, Long> {
    List<Resort> findByOwner(User owner);
    List<Resort> findByLocationContainingIgnoreCase(String location);
}
