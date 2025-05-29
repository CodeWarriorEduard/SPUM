package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.ItemTypeDTO;
import com.example.spum_backend.dto.response.ItemTypeResponseDTO;

import java.util.List;

public interface ItemTypeService {

    ItemTypeResponseDTO addItemType(ItemTypeDTO itemTypeDTO);
    ItemTypeResponseDTO updateItemType(Long id, ItemTypeDTO itemTypeDTO);
    void deleteItemType(Long id);
    List<ItemTypeResponseDTO> getAllItemTypes();
    ItemTypeResponseDTO findItemTypeById(Long id);
}
