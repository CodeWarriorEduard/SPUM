package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.ItemTypeDTO;
import com.example.spum_backend.entity.ItemType;
import com.example.spum_backend.exception.notFound.ItemTypeNotFoundException;
import com.example.spum_backend.mapper.itemTypeMapper;
import com.example.spum_backend.repository.ItemTypeRepository;
import com.example.spum_backend.service.interfaces.ItemTypeService;
import com.example.spum_backend.service.interfaces.internal.ItemTypeServiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemTypeServiceImpl implements ItemTypeService, ItemTypeServiceEntity {

    private final ItemTypeRepository itemTypeRepository;
    private final itemTypeMapper itemTypeMapper;

    public ItemTypeServiceImpl(ModelMapper modelMapper, ItemTypeRepository itemTypeRepository, itemTypeMapper itemTypeMapper) {
        this.itemTypeRepository = itemTypeRepository;
        this.itemTypeMapper = itemTypeMapper;
    }

    @Override
    public ItemTypeDTO addItemType(ItemTypeDTO itemTypeDTO) {
        ItemType itemType = ItemType
                .builder()
                .itemTypeName(itemTypeDTO.getItemTypeName())
                .build();
        return itemTypeMapper.toDTO(itemTypeRepository.save(itemType));
    }

    @Override
    public ItemTypeDTO updateItemType(Long id, ItemTypeDTO idItemTypeDTO) {
        ItemType itemType = getItemTypeById(id);

        itemType.setItemTypeName(idItemTypeDTO.getItemTypeName());
        return itemTypeMapper.toDTO(itemTypeRepository.save(itemType));
    }

    @Override
    public void deleteItemType(Long id) {
        itemTypeRepository.delete(getItemTypeById(id));
    }

    @Override
    public List<ItemTypeDTO> getAllItemTypes() {
        return itemTypeRepository.findAll().stream().map(itemTypeMapper::toDTO).toList();
    }

    @Override
    public ItemTypeDTO findItemTypeById(Long id) {
        return itemTypeMapper.toDTO(getItemTypeById(id));
    }

    public ItemType getItemTypeById(Long id) {
        return itemTypeRepository.findById(id)
                .orElseThrow(() -> new ItemTypeNotFoundException("Item Type Not Found"));
    }
}
