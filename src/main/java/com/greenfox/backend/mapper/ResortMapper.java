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

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "pricePerNight", source = "pricePerNight")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "amenities", source = "amenities")
    @Mapping(target = "policies", source = "policies")
    @Mapping(target = "coordinates.latitude", source = "latitude")
    @Mapping(target = "coordinates.longitude", source = "longitude")
    Resort fromCreateDto(ResortCreateDto dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "pricePerNight", source = "pricePerNight")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "amenities", source = "amenities")
    @Mapping(target = "policies", source = "policies")
    @Mapping(target = "coordinates.latitude", source = "latitude")
    @Mapping(target = "coordinates.longitude", source = "longitude")
    Resort fromUpdateDto(ResortUpdateDto dto);
}
