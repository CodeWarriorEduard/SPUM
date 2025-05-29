package com.example.spum_backend.dto.request.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.example.spum_backend.enumeration.BookingStatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateRequestDTO {
    private Long bookingId;
    private LocalDateTime startTime;
    private Long itemId;
    private String email;
    private BookingStatusEnum status;
}
