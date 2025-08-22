package com.greenfox.backend.service.impl;

import com.greenfox.backend.entity.Review;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.repository.ReviewRepository;
import com.greenfox.backend.repository.ResortRepository;
import com.greenfox.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ResortRepository resortRepository;

    @PreAuthorize("hasRole('ADMIN') or @sec.canManageBooking(authentication, #bookingId)")
    @Override
    @Transactional
    public Review addReview(User user, Resort resort, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Review review = Review.builder()
                .user(user)
                .resort(resort)
                .rating(rating)
                .comment(comment)
                .build();

        Review saved = reviewRepository.save(review);

        // обновляем агрегаты курорта
        Resort target = resortRepository.findById(resort.getId())
                .orElseThrow(() -> new IllegalArgumentException("Resort not found"));
        long newCount = target.getRatingCount() + 1;
        double newAvg = ((target.getRatingAverage() * target.getRatingCount()) + rating) / (double) newCount;

        target.setRatingCount(newCount);
        target.setRatingAverage(newAvg);
        resortRepository.save(target);

        return saved;
    }

    @Override
    public List<Review> getReviewsForResort(Resort resort) {
        return reviewRepository.findByResort(resort);
    }
}
