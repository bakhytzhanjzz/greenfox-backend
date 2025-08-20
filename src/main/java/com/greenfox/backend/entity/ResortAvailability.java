package com.greenfox.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "resort_availability",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_resort_date", columnNames = {"resort_id", "date"})
        },
        indexes = {
                @Index(name = "idx_resort_availability_resort", columnList = "resort_id"),
                @Index(name = "idx_resort_availability_date", columnList = "date")
        })
public class ResortAvailability extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resort_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_availability_resort"))
    private Resort resort;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "is_available", nullable = false)
    private boolean available = true;
}
