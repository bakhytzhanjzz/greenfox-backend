package com.greenfox.backend.dto.auth;

import com.greenfox.backend.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpRequest {
    @NotBlank
    private String phoneNumber;

    private Role role; // Optional — use to register new users with correct role
}
