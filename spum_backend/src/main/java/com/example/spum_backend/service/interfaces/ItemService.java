package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.ItemRequestDTO;
import com.example.spum_backend.dto.response.ItemResponseDTO;
import com.example.spum_backend.entity.Item;
import com.example.spum_backend.entity.ItemType;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemResponseDTO> findAllItems();
    ItemResponseDTO findItemById(Long id);
    List<ItemResponseDTO> findItemByName(String nombre);
    ItemResponseDTO addItem(ItemRequestDTO item);
    ItemResponseDTO updateItem(Long id, ItemRequestDTO item);
    void deleteItemById(Long id);
    List<ItemResponseDTO> findItemByItemType(Long id);

}
