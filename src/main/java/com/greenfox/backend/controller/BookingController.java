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

    @PostMapping("/user/{userId}")
    public ResponseEntity<BookingDto> create(
            @PathVariable Long userId,
            @Valid @RequestBody BookingCreateDto dto) {
        User user = userService.getById(userId);
        Resort resort = resortService.getResortById(dto.getResortId());
        Booking booking = bookingService.createBooking(user, resort, dto.getCheckInDate(), dto.getCheckOutDate(), dto.getGuests());
        return ResponseEntity.ok(bookingMapper.toDto(booking));
    }

    @PostMapping("/{bookingId}/cancel/user/{userId}")
    public ResponseEntity<Void> cancel(@PathVariable Long bookingId, @PathVariable Long userId) {
        User user = userService.getById(userId);
        bookingService.cancelBooking(bookingId, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getForUser(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(
                bookingService.getBookingsForUser(user).stream()
                        .map(bookingMapper::toDto).toList()
        );
    }

    @GetMapping("/resort/{resortId}")
    public ResponseEntity<List<BookingDto>> getForResort(@PathVariable Long resortId) {
        Resort resort = resortService.getResortById(resortId);
        return ResponseEntity.ok(
                bookingService.getBookingsForResort(resort).stream()
                        .map(bookingMapper::toDto).toList()
        );
    }
}
