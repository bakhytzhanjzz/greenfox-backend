package com.greenfox.backend.controller;

import com.greenfox.backend.dto.UserDto;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.enums.UserRole;
import com.greenfox.backend.mapper.UserMapper;
import com.greenfox.backend.repository.UserRepository;
import com.greenfox.backend.security.JwtUtil;
import lombok.*;

        import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
        import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByPhoneNumber(req.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone already in use");
        }
        User user = User.builder()
                .phoneNumber(req.getPhoneNumber())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(UserRole.CLIENT)
                .build();
        User saved = userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getPhoneNumber(), req.getPassword())
        );
        User user = userRepository.findByPhoneNumber(req.getPhoneNumber()).orElseThrow();
        String token = jwtUtil.generateToken(user.getPhoneNumber(), user.getRole().name(), user.getId());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    static class RegisterRequest {
        private String phoneNumber;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    static class LoginRequest {
        private String phoneNumber;
        private String password;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    static class AuthResponse {
        private String token;
    }
}
