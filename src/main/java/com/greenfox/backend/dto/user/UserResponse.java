package com.greenfox.backend.dto.user;

import com.greenfox.backend.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private Role role;
    private Boolean isVerified;
}
