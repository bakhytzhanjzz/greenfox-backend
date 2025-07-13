package com.greenfox.backend.repository;

import com.greenfox.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find by email (for login and checking duplicates)
    Optional<User> findByEmail(String email);

    // Optional: Find by phone number (if you allow phone login/lookup)
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Check if user exists by email
    boolean existsByEmail(String email);

    // Check if user exists by phone number
    boolean existsByPhoneNumber(String phoneNumber);
}