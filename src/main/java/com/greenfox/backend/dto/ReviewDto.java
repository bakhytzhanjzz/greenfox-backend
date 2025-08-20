package com.greenfox.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewDto {
    private Long id;
    private Long userId;
    private Long resortId;
    private int rating;
    private String comment;
}

