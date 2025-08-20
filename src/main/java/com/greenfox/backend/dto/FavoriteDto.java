package com.greenfox.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FavoriteDto {
    private Long id;
    private Long userId;
    private Long resortId;
}

