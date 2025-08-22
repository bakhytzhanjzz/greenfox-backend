package com.greenfox.backend.entity;

import com.greenfox.backend.entity.enums.UserRole;
import jakarta.persistence.*;
        import jakarta.validation.constraints.*;
        import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "idx_users_phone", columnList = "phone_number"),
                @Index(name = "idx_users_role", columnList = "role")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_phone", columnNames = "phone_number")
        })
public class User extends BaseEntity {

    @Column(name = "phone_number", nullable = false, length = 20)
    @NotBlank
    private String phoneNumber; // логин по номеру телефона (см. твой документ про Firebase/JWT) :contentReference[oaicite:3]{index=3}

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Email
    @Column(name = "email", length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role;

    @Column(nullable = false)
    private String password;

}
