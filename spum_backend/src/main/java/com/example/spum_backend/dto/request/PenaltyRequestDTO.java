package com.example.spum_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyRequestDTO {
    String description;
    LocalDateTime penaltyDate;
    Long penaltyType;
    String email;
}
