package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.ItemRequestDTO;
import com.example.spum_backend.dto.response.ItemResponseDTO;
import com.example.spum_backend.entity.Item;
import com.example.spum_backend.entity.ItemType;
import com.example.spum_backend.exception.notFound.ItemNotFoundException;
import com.example.spum_backend.exception.notFound.ItemTypeNotFoundException;
import com.example.spum_backend.mapper.ItemMapper;
import com.example.spum_backend.repository.ItemRepository;
import com.example.spum_backend.repository.ItemTypeRepository;
import com.example.spum_backend.service.interfaces.ItemService;
import com.example.spum_backend.service.interfaces.internal.ItemServiceEntity;
import com.example.spum_backend.service.interfaces.internal.ItemTypeServiceEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService, ItemServiceEntity {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemTypeServiceEntity itemTypeService;
    private final ItemTypeRepository itemTypeRepository;


    @Override
    public List<ItemResponseDTO> findAllItems() {
        return itemRepository.findAll()
                .stream().map(itemMapper::toDto).toList();
    }

    @Override
    public ItemResponseDTO findItemById(Long id) {
        return itemRepository.findById(id)
                .map(itemMapper::toDto)
                .orElseThrow(()-> new ItemNotFoundException("Item not found"));
    }

    @Override
    public List<ItemResponseDTO> findItemByName(String nombre) {
        List<Item> items = itemRepository.findByItemNameContainingIgnoreCase(nombre);
        if (items.isEmpty()) {
            throw new ItemNotFoundException("No se encontró ningún item con el nombre que contiene: " + nombre);
        }
        return items.stream()
                .map(itemMapper::toDto)
                .toList();
    }
    @Override
    public ItemResponseDTO addItem(ItemRequestDTO item) {
        Item itemToSave = itemMapper.toEntity(item);

        // Look for the item type
        ItemType itemType = itemTypeService.getItemTypeById(item.getItemType());

        itemToSave.setItemType(itemType);

        return itemMapper.toDto(itemRepository.save(itemToSave));
    }

    @Override
    public ItemResponseDTO updateItem(Long id, ItemRequestDTO item) {
        Item itemToUpdate = getItemById(id);
        itemToUpdate.setItemName
                (item.getItemName()==null?itemToUpdate.getItemName():item.getItemName());
        itemToUpdate.setItemDescription
                (item.getItemDescription()==null?itemToUpdate.getItemDescription():item.getItemDescription());
        itemToUpdate.setItemQuantity
                (item.getItemQuantity()==null?itemToUpdate.getItemQuantity():item.getItemQuantity());

        if(item.getItemType()!=null){
            ItemType itemType = itemTypeService.getItemTypeById(item.getItemType());
            itemToUpdate.setItemType(itemType);
        }

        return itemMapper.toDto(itemRepository.save(itemToUpdate));
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.delete(getItemById(id));
    }

    @Override
    public List<ItemResponseDTO> findItemByItemType(Long id) {
        return itemRepository.findItemByItemType(itemTypeRepository.findById(id)
                .orElseThrow(()-> new ItemTypeNotFoundException("Item Type with id: " + id + " not found")))
                .stream().map(itemMapper::toDto).toList();
    }


    // Service methods for inner tasks.
    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item with id "+ id +" Not found"));
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
}
