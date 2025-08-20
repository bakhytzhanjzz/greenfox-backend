package com.greenfox.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "reviews",
        indexes = {
                @Index(name = "idx_reviews_resort", columnList = "resort_id"),
                @Index(name = "idx_reviews_user", columnList = "user_id")
        })
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resort_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_resort"))
    private Resort resort;

    @Min(1) @Max(5)
    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comment", length = 2000)
    private String comment;
}
