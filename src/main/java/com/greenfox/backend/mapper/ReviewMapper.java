package com.greenfox.backend.mapper;

import com.greenfox.backend.dto.ReviewDto;
import com.greenfox.backend.dto.ReviewCreateDto;
import com.greenfox.backend.entity.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "resort.id", target = "resortId")
    ReviewDto toDto(Review review);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "comment", source = "comment")
    Review fromCreateDto(ReviewCreateDto dto);
}

