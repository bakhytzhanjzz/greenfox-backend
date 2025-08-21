package com.greenfox.backend.controller;

import com.greenfox.backend.dto.FavoriteDto;
import com.greenfox.backend.dto.FavoriteCreateDto;
import com.greenfox.backend.entity.Favorite;
import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.entity.User;
import com.greenfox.backend.mapper.FavoriteMapper;
import com.greenfox.backend.service.FavoriteService;
import com.greenfox.backend.service.ResortService;
import com.greenfox.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final ResortService resortService;
    private final FavoriteMapper favoriteMapper;

    @PostMapping("/user/{userId}")
    public ResponseEntity<FavoriteDto> add(
            @PathVariable Long userId,
            @Valid @RequestBody FavoriteCreateDto dto) {
        User user = userService.getById(userId);
        Resort resort = resortService.getResortById(dto.getResortId());
        Favorite favorite = favoriteService.addFavorite(user, resort);
        return ResponseEntity.ok(favoriteMapper.toDto(favorite));
    }

    @DeleteMapping("/user/{userId}/resort/{resortId}")
    public ResponseEntity<Void> remove(@PathVariable Long userId, @PathVariable Long resortId) {
        User user = userService.getById(userId);
        Resort resort = resortService.getResortById(resortId);
        favoriteService.removeFavorite(user, resort);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDto>> getFavorites(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(
                favoriteService.getFavorites(user).stream()
                        .map(r -> FavoriteDto.builder()
                                .id(null) // можно вернуть null, если не нужно id записи
                                .userId(userId)
                                .resortId(r.getId())
                                .build())
                        .toList()
        );
    }
}
