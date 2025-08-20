package com.greenfox.backend.mapper;

import com.greenfox.backend.dto.BookingDto;
import com.greenfox.backend.dto.BookingCreateDto;
import com.greenfox.backend.entity.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "resort.id", target = "resortId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "status", target = "status")
    BookingDto toDto(Booking booking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "resort", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    Booking fromCreateDto(BookingCreateDto dto);
}
