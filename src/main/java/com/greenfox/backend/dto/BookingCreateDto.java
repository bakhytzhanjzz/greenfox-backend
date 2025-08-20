package com.greenfox.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingCreateDto {
    private Long resortId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;
    private String specialRequests;
}
