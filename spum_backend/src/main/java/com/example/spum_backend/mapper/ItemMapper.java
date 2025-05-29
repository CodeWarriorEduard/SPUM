package com.example.spum_backend.mapper;

import com.example.spum_backend.dto.request.ItemRequestDTO;
import com.example.spum_backend.dto.response.ItemResponseDTO;
import com.example.spum_backend.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "itemType", target = "itemType")
    ItemResponseDTO toDto(Item item);

    @Mapping(source = "itemType", target = "itemType.itemTypeId")
    Item toEntity(ItemRequestDTO item);
}
