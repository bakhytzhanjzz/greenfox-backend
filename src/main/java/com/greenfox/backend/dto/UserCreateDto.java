package com.greenfox.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserCreateDto {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
}
