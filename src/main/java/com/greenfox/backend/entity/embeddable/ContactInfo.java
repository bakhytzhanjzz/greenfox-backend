package com.greenfox.backend.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Embeddable
public class ContactInfo {
    private String phone;
    private String email;
    private String website;
}
