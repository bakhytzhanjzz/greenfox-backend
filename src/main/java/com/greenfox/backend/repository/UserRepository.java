package com.greenfox.backend.repository;

import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
}
