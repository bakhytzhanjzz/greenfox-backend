package com.greenfox.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResortDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private BigDecimal pricePerNight;
    private double ratingAverage;
    private long ratingCount;
    private List<String> images;
    private Set<String> amenities;
    private String policies;
    private Double latitude;
    private Double longitude;
    private Long ownerId;
}

