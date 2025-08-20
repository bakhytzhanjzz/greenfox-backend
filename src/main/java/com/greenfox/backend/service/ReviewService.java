package com.greenfox.backend.service;

import com.greenfox.backend.entity.Review;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.Resort;

import java.util.List;

public interface ReviewService {
    Review addReview(User user, Resort resort, int rating, String comment);
    List<Review> getReviewsForResort(Resort resort);
}
