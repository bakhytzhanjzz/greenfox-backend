package com.greenfox.backend.repository;

import com.greenfox.backend.entity.Review;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByResort(Resort resort);
    List<Review> findByUser(User user);
}
