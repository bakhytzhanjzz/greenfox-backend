package com.greenfox.backend.service;

import com.greenfox.backend.dto.auth.*;
import com.greenfox.backend.entity.OtpCode;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.enums.Role;
import com.greenfox.backend.repository.OtpCodeRepository;
import com.greenfox.backend.repository.UserRepository;
import com.greenfox.backend.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpCodeRepository otpCodeRepository;


    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .profileImageUrl(request.getProfileImageUrl())
                .role(request.getRole())
                .isVerified(false)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authToken);

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .token(token)
                .build();
    }

    public void requestOtp(OtpRequest request) {
        String code = String.format("%06d", new Random().nextInt(999999));

        OtpCode otp = OtpCode.builder()
                .phoneNumber(request.getPhoneNumber())
                .code(code)
                .used(false)
                .createdAt(LocalDateTime.now())
                .build();

        otpCodeRepository.save(otp);

        // Mock SMS - just print it
        System.out.println("OTP for " + request.getPhoneNumber() + ": " + code);
    }

    public AuthResponse verifyOtp(OtpVerifyRequest request) {
        OtpCode otp = otpCodeRepository.findTopByPhoneNumberAndUsedFalseOrderByCreatedAtDesc(request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("No active OTP for this phone"));

        if (!otp.getCode().equals(request.getCode())) {
            throw new IllegalArgumentException("Incorrect OTP code");
        }

        if (otp.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new IllegalArgumentException("OTP code has expired");
        }

        otp.setUsed(true);
        otpCodeRepository.save(otp);

        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .phoneNumber(request.getPhoneNumber())
                            .role(Role.CLIENT) // or PARTNER — needs front-end to send role in /request-otp
                            .isVerified(true)
                            .build();
                    return userRepository.save(newUser);
                });

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .token(token)
                .build();
    }


}
