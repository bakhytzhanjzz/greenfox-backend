package com.greenfox.backend.service.impl;

import com.greenfox.backend.entity.Booking;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.ResortAvailability;
import com.greenfox.backend.entity.enums.BookingStatus;
import com.greenfox.backend.repository.BookingRepository;
import com.greenfox.backend.repository.ResortAvailabilityRepository;
import com.greenfox.backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ResortAvailabilityRepository availabilityRepository;

    @Override
    @Transactional
    public Booking createBooking(User user, Resort resort, LocalDate checkIn, LocalDate checkOut, int guests) {
        if (!checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        // Проверка доступности (просто: все даты должны быть available)
        List<ResortAvailability> days = availabilityRepository.findByResortAndDateBetween(resort, checkIn, checkOut.minusDays(1));
        if (days.isEmpty() || days.stream().anyMatch(d -> !d.isAvailable())) {
            throw new IllegalStateException("Resort is not available for selected dates");
        }

        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        BigDecimal totalPrice = resort.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = Booking.builder()
                .user(user)
                .resort(resort)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .guests(guests)
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        // Блокируем даты
        days.forEach(d -> d.setAvailable(false));
        availabilityRepository.saveAll(days);

        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId, User user) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getUser().equals(user)) {
            throw new SecurityException("Not allowed to cancel this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        // Освободим даты
        List<ResortAvailability> days = availabilityRepository.findByResortAndDateBetween(
                booking.getResort(), booking.getCheckInDate(), booking.getCheckOutDate().minusDays(1));
        days.forEach(d -> d.setAvailable(true));
        availabilityRepository.saveAll(days);
    }

    @Override
    public List<Booking> getBookingsForUser(User user) {
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<Booking> getBookingsForResort(Resort resort) {
        return bookingRepository.findByResort(resort);
    }
}
