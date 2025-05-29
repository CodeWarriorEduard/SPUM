package com.example.spum_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyRequestDTO {
    @NotBlank String description;
    @NotBlank LocalDateTime penaltyDate;
    @NotNull Long penaltyType;
    @NotBlank String email;
}
