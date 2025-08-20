package com.greenfox.backend.service;

import com.greenfox.backend.entity.Favorite;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.entity.Resort;

import java.util.List;

public interface FavoriteService {
    Favorite addFavorite(User user, Resort resort);
    void removeFavorite(User user, Resort resort);
    List<Resort> getFavorites(User user);
}
