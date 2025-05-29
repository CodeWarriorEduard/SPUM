package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.ItemRequestDTO;
import com.example.spum_backend.dto.request.ItemUpdateRequest;
import com.example.spum_backend.dto.response.ItemResponseDTO;
import com.example.spum_backend.service.interfaces.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public List<ItemResponseDTO> getAllItems() {
        return itemService.findAllItems();
    }

    @GetMapping("/{id}")
    public ItemResponseDTO getItemById(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @PostMapping("/add")
    public ItemResponseDTO addItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        return itemService.addItem(itemRequestDTO);
    }

    @PutMapping("/{id}")
    public ItemResponseDTO updateItem(@PathVariable Long id, @RequestBody ItemUpdateRequest itemRequestDTO) {
        return itemService.updateItem(itemRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }
}
