package com.greenfox.backend.dto.auth;

import com.greenfox.backend.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String profileImageUrl;

    private Role role;

    private boolean isVerified;

    private String token; // JWT
}
