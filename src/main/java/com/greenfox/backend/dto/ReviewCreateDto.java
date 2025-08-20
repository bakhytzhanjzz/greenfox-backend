package com.greenfox.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewCreateDto {
    private Long resortId;
    private int rating;
    private String comment;
}
