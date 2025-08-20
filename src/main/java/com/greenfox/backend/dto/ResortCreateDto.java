package com.greenfox.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResortCreateDto {
    private String name;
    private String description;
    private String location;
    private BigDecimal pricePerNight;
    private List<String> images;
    private Set<String> amenities;
    private String policies;
    private Double latitude;
    private Double longitude;
}
