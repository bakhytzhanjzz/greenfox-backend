package com.greenfox.backend.service;

import com.greenfox.backend.entity.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;

public interface UserService {
    User registerUser(String phoneNumber, String firstName, String lastName, String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    User getById(Long id);

    User save(User user);
    User updateUserProfile(User user, String firstName, String lastName, String email);

    // 🔹 new helper for extracting current user from JWT token
    User getCurrentUser(Authentication authentication);
}