package com.example.spum_backend.dto.request.booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateRequestDTO {
    private LocalDateTime startTime;
    private Long itemId;
    private String email;
}
