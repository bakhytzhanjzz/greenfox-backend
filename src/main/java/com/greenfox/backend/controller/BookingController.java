package com.greenfox.backend.controller;

import com.greenfox.backend.dto.BookingDto;
import com.greenfox.backend.dto.BookingCreateDto;
import com.greenfox.backend.entity.Booking;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.mapper.BookingMapper;
import com.greenfox.backend.service.BookingService;
import com.greenfox.backend.service.ResortService;
import com.greenfox.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final ResortService resortService;
    private final BookingMapper bookingMapper;

    // ✅ Create booking for the current authenticated user
    @PostMapping
    public ResponseEntity<BookingDto> create(
            @Valid @RequestBody BookingCreateDto dto,
            Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        Resort resort = resortService.getResortById(dto.getResortId());

        Booking booking = bookingService.createBooking(
                user,
                resort,
                dto.getCheckInDate(),
                dto.getCheckOutDate(),
                dto.getGuests()
        );
        booking.setSpecialRequests(dto.getSpecialRequests());

        return ResponseEntity.ok(bookingMapper.toDto(booking));
    }

    // ✅ Cancel booking for the current user
    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long bookingId, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        bookingService.cancelBooking(bookingId, user);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get bookings of the current user
    @GetMapping("/me")
    public ResponseEntity<List<BookingDto>> getMyBookings(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(
                bookingService.getBookingsForUser(user).stream()
                        .map(bookingMapper::toDto)
                        .toList()
        );
    }

    // ✅ Keep endpoint for partner/admin to view resort bookings
    @GetMapping("/resort/{resortId}")
    public ResponseEntity<List<BookingDto>> getForResort(@PathVariable Long resortId) {
        Resort resort = resortService.getResortById(resortId);
        return ResponseEntity.ok(
                bookingService.getBookingsForResort(resort).stream()
                        .map(bookingMapper::toDto)
                        .toList()
        );
    }
}
