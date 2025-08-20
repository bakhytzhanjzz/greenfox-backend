package com.greenfox.backend.repository;

import com.greenfox.backend.entity.Favorite;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndResort(User user, Resort resort);
    void deleteByUserAndResort(User user, Resort resort);
}
