package com.greenfox.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_favorite_user_resort", columnNames = {"user_id", "resort_id"})
        },
        indexes = {
                @Index(name = "idx_favorites_user", columnList = "user_id")
        })
public class Favorite extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resort_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_resort"))
    private Resort resort;
}
