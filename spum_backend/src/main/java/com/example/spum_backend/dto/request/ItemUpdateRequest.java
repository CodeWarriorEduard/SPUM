package com.example.spum_backend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateRequest {

    Long itemId;
    String itemName;
    String itemDescription;
    Long itemQuantity;
    Long itemTypeId;
}
