package com.greenfox.backend.service.impl;

import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.enums.UserRole;
import com.greenfox.backend.repository.UserRepository;
import com.greenfox.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User registerUser(String phoneNumber, String firstName, String lastName, String email) {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        User user = User.builder()
                .phoneNumber(phoneNumber)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(UserRole.CLIENT) // default role
                .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserProfile(User user, String firstName, String lastName, String email) {
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);
        if (email != null) user.setEmail(email);

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        String phoneNumber = authentication.getName(); // subject in JWT = phone number
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
    }
}
