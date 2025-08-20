package com.greenfox.backend.service.impl;

import com.greenfox.backend.entity.Favorite;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.repository.FavoriteRepository;
import com.greenfox.backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public Favorite addFavorite(User user, Resort resort) {
        return favoriteRepository.findByUserAndResort(user, resort)
                .orElseGet(() -> favoriteRepository.save(
                        Favorite.builder().user(user).resort(resort).build()
                ));
    }

    @Override
    @Transactional
    public void removeFavorite(User user, Resort resort) {
        favoriteRepository.deleteByUserAndResort(user, resort);
    }

    @Override
    public List<Resort> getFavorites(User user) {
        return favoriteRepository.findByUser(user).stream()
                .map(Favorite::getResort)
                .toList();
    }
}
