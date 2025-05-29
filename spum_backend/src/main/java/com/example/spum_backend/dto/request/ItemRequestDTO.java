package com.example.spum_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDTO{
    @NotBlank String itemName;
    @NotBlank String itemDescription;
    @NotNull Long itemQuantity;
    @NotNull Long itemType;
}
