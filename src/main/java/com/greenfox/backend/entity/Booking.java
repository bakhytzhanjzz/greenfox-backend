package com.greenfox.backend.entity;

import com.greenfox.backend.entity.enums.BookingStatus;
import jakarta.persistence.*;
        import jakarta.validation.constraints.*;
        import lombok.*;

        import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "bookings",
        indexes = {
                @Index(name = "idx_bookings_user", columnList = "user_id"),
                @Index(name = "idx_bookings_resort", columnList = "resort_id"),
                @Index(name = "idx_bookings_checkin", columnList = "check_in_date")
        })
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resort_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_resort"))
    private Resort resort;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_user"))
    private User user;

    @NotNull
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @NotNull
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Min(1)
    @Column(name = "guests", nullable = false)
    private int guests;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status = BookingStatus.CONFIRMED; // как в примере ответа API :contentReference[oaicite:6]{index=6}

    @Column(name = "special_requests", length = 1000)
    private String specialRequests;
}
