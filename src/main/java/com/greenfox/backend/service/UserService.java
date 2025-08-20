package com.greenfox.backend.service;

import com.greenfox.backend.entity.User;
import java.util.Optional;

public interface UserService {
    User registerUser(String phoneNumber, String firstName, String lastName, String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    User getById(Long id);
}
