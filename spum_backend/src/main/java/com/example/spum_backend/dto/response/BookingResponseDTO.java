package com.example.spum_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO{
    String userName;
    String userLastName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String item;
}
