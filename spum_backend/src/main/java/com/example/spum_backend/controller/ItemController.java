package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.ItemRequestDTO;
import com.example.spum_backend.dto.response.ItemResponseDTO;
import com.example.spum_backend.service.interfaces.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin(value = "http://localhost:5173")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        return ResponseEntity.ok(itemService.findAllItems());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemResponseDTO>> searchItemByName(@RequestParam String name) {
        return ResponseEntity.ok(itemService.findItemByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findItemById(id));
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<List<ItemResponseDTO>> getItemByItemTypeName(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findItemByItemType(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> addItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(itemRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(@PathVariable Long id, @RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.ok(itemService.updateItem(id, itemRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return ResponseEntity.noContent().build();
    }
}
