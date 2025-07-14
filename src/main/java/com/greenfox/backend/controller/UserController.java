package com.greenfox.backend.controller;

import com.greenfox.backend.dto.user.UpdateUserRequest;
import com.greenfox.backend.dto.user.UserResponse;
import com.greenfox.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        System.out.println("AUTH = " + authentication);
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest request,
            Authentication authentication) {
        System.out.println("AUTH = " + authentication);

        return ResponseEntity.ok(userService.updateCurrentUser(request, authentication));
    }
}
