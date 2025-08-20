package com.greenfox.backend.mapper;

import com.greenfox.backend.dto.ResortDto;
import com.greenfox.backend.dto.ResortCreateDto;
import com.greenfox.backend.dto.ResortUpdateDto;
import com.greenfox.backend.entity.Resort;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResortMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "coordinates.latitude", target = "latitude")
    @Mapping(source = "coordinates.longitude", target = "longitude")
    ResortDto toDto(Resort resort);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "ratingAverage", constant = "0.0")
    @Mapping(target = "ratingCount", constant = "0L")
    @Mapping(target = "coordinates.latitude", source = "latitude")
    @Mapping(target = "coordinates.longitude", source = "longitude")
    Resort fromCreateDto(ResortCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "ratingAverage", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "coordinates.latitude", source = "latitude")
    @Mapping(target = "coordinates.longitude", source = "longitude")
    Resort fromUpdateDto(ResortUpdateDto dto);
}
