package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.ItemRequestDTO;
import com.example.spum_backend.dto.request.ItemUpdateRequest;
import com.example.spum_backend.dto.response.ItemResponseDTO;
import com.example.spum_backend.entity.Item;
import com.example.spum_backend.entity.ItemType;
import com.example.spum_backend.exception.ItemNotFoundException;
import com.example.spum_backend.exception.ItemTypeNotFoundException;
import com.example.spum_backend.repository.ItemRepository;
import com.example.spum_backend.repository.ItemTypeRepository;
import com.example.spum_backend.service.interfaces.ItemService;
import com.example.spum_backend.service.interfaces.internal.ItemServiceEntity;
import com.example.spum_backend.service.interfaces.internal.ItemTypeServiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ItemServiceImpl implements ItemService, ItemServiceEntity {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final ItemTypeServiceEntity itemTypeService;

    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper, ItemTypeServiceEntity itemTypeService) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.itemTypeService = itemTypeService;
    }

    @Override
    public List<ItemResponseDTO> findAllItems() {
        System.out.println(itemRepository.findAll());
        return itemRepository.findAll()
                .stream().map(item -> modelMapper.map(item, ItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDTO findItemById(Long id) {
        Item item = getItemById(id);
        return modelMapper.map(item, ItemResponseDTO.class);
    }

    @Override
    public ItemResponseDTO addItem(ItemRequestDTO item) {
        Item itemToSave = modelMapper.map(item, Item.class);

        // Look for the item type
        ItemType itemType = itemTypeService.getItemTypeById(item.getItemType());

        itemToSave.setItemType(itemType);

        return modelMapper.map(itemRepository.save(itemToSave), ItemResponseDTO.class);
    }

    @Override
    public void deleteItemById(Long id) {
        Item item = getItemById(id);
        itemRepository.delete(item);
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

    @Override
    public ItemResponseDTO updateItem(ItemUpdateRequest item) {

        Item itemToUpdate = getItemById(item.getItemId());

        if(!item.getItemDescription().isBlank()){
            itemToUpdate.setItemDescription(item.getItemDescription());
        }
        if(!item.getItemName().isBlank()){
            itemToUpdate.setItemName(item.getItemName());
        }
        if (item.getItemQuantity()!=null){
            itemToUpdate.setItemQuantity(item.getItemQuantity());
        }

        if(item.getItemTypeId()!=null){
            ItemType type = itemTypeService.getItemTypeById(item.getItemTypeId());
            itemToUpdate.setItemType(type);
        }
        return modelMapper.map(itemRepository.save(itemToUpdate), ItemResponseDTO.class);
    }
}
