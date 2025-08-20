package com.greenfox.backend.mapper;

import com.greenfox.backend.dto.FavoriteDto;
import com.greenfox.backend.dto.FavoriteCreateDto;
import com.greenfox.backend.entity.Favorite;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "resort.id", target = "resortId")
    FavoriteDto toDto(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "resort", ignore = true)
    Favorite fromCreateDto(FavoriteCreateDto dto);
}
