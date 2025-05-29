package com.example.spum_backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyResponseDTO{
    String description;
    LocalDateTime penaltyDate;
    Long penaltyType;
    String studentName;
    String email;
}
