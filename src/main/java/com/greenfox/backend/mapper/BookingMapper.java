package com.greenfox.backend.mapper;

import com.greenfox.backend.dto.BookingDto;
import com.greenfox.backend.dto.BookingCreateDto;
import com.greenfox.backend.entity.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "resort.id", target = "resortId")
    @Mapping(source = "user.id", target = "userId")
    BookingDto toDto(Booking booking);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "checkInDate", source = "checkInDate")
    @Mapping(target = "checkOutDate", source = "checkOutDate")
    @Mapping(target = "guests", source = "guests")
    @Mapping(target = "specialRequests", source = "specialRequests")
    Booking fromCreateDto(BookingCreateDto dto);
}
