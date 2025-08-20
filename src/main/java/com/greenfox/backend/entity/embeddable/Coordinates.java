package com.greenfox.backend.entity.embeddable;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Embeddable
public class Coordinates {
    private Double latitude;
    private Double longitude;
}
