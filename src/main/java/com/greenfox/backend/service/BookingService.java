package com.greenfox.backend.service;

import com.greenfox.backend.entity.Booking;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.Resort;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking createBooking(User user, Resort resort, LocalDate checkIn, LocalDate checkOut, int guests);
    void cancelBooking(Long bookingId, User user);
    List<Booking> getBookingsForUser(User user);
    List<Booking> getBookingsForResort(Resort resort);
}
