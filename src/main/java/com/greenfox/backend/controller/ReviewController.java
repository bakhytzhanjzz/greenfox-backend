package com.greenfox.backend.controller;

import com.greenfox.backend.dto.ReviewDto;
import com.greenfox.backend.dto.ReviewCreateDto;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.Review;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.mapper.ReviewMapper;
import com.greenfox.backend.service.ReviewService;
import com.greenfox.backend.service.ResortService;
import com.greenfox.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ResortService resortService;
    private final ReviewMapper reviewMapper;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ReviewDto> add(
            @PathVariable Long userId,
            @Valid @RequestBody ReviewCreateDto dto) {
        User user = userService.getById(userId);
        Resort resort = resortService.getResortById(dto.getResortId());
        Review review = reviewService.addReview(user, resort, dto.getRating(), dto.getComment());
        return ResponseEntity.ok(reviewMapper.toDto(review));
    }

    @GetMapping("/resort/{resortId}")
    public ResponseEntity<List<ReviewDto>> getForResort(@PathVariable Long resortId) {
        Resort resort = resortService.getResortById(resortId);
        return ResponseEntity.ok(
                reviewService.getReviewsForResort(resort).stream()
                        .map(reviewMapper::toDto).toList()
        );
    }
}
