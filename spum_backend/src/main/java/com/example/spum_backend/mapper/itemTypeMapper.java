package com.example.spum_backend.mapper;

import com.example.spum_backend.dto.request.ItemTypeDTO;
import com.example.spum_backend.dto.response.ItemTypeResponseDTO;
import com.example.spum_backend.entity.ItemType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface itemTypeMapper {

    ItemTypeResponseDTO toDTO(ItemType itemType);

    ItemType toEntity(ItemTypeDTO itemTypeDTO);
}
