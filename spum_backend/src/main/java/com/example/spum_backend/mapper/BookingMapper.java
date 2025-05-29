package com.example.spum_backend.mapper;

import com.example.spum_backend.dto.request.booking.BookingCreateRequestDTO;
import com.example.spum_backend.dto.request.booking.BookingUpdateRequestDTO;
import com.example.spum_backend.dto.response.BookingResponseDTO;
import com.example.spum_backend.entity.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    // Booking → DTO
    @Mapping(source = "student.user.userName", target = "userName")
    @Mapping(source = "student.user.userLastName", target = "userLastName")
    @Mapping(source = "item.itemName", target = "item")
    BookingResponseDTO toDto(Booking booking);

    // BookingCreateRequestDTO → Booking (ignorando relaciones)
    @Mapping(target = "bookingId", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "bookingStatus", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "item", ignore = true)
    Booking toEntity(BookingCreateRequestDTO dto);

    // Actualización del estado
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Booking updateEntityFromDto(BookingUpdateRequestDTO dto, @MappingTarget Booking booking);
}
