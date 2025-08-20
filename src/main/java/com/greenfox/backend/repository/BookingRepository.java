package com.greenfox.backend.repository;

import com.greenfox.backend.entity.Booking;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByResort(Resort resort);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByCheckInDateBetween(LocalDate start, LocalDate end);
}
