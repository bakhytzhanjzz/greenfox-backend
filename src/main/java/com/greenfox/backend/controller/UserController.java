package com.greenfox.backend.controller;

import com.greenfox.backend.dto.UserDto;
import com.greenfox.backend.dto.UserCreateDto;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.mapper.UserMapper;
import com.greenfox.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto dto) {
        User user = userService.registerUser(
                dto.getPhoneNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.getById(id)));
    }

    @GetMapping("/by-phone/{phone}")
    public ResponseEntity<UserDto> getByPhone(@PathVariable String phone) {
        return userService.findByPhoneNumber(phone)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 New endpoint: current user profile
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String phoneNumber = authentication.getName();

        return userService.findByPhoneNumber(phoneNumber)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 New endpoint: update current user profile
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateCurrentUser(
            Authentication authentication,
            @Valid @RequestBody UserCreateDto dto) {

        String phoneNumber = authentication.getName();

        User user = userService.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        User updated = userService.updateUserProfile(user, dto.getFirstName(), dto.getLastName(), dto.getEmail());
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
}
