package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.ItemTypeDTO;
import com.example.spum_backend.dto.response.ItemTypeResponseDTO;
import com.example.spum_backend.service.interfaces.ItemTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(value = "http://localhost:5173")
public class ItemTypeController {

    private final ItemTypeService itemTypeService;

    public ItemTypeController(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    @PostMapping
    public ResponseEntity<ItemTypeResponseDTO> addItemType(@RequestBody @Valid ItemTypeDTO itemType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemTypeService.addItemType(itemType));
    }

    @GetMapping
    public ResponseEntity<List<ItemTypeResponseDTO>> getAllItemsType() {
        return ResponseEntity.ok(itemTypeService.getAllItemTypes());
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemTypeResponseDTO> getItemTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(itemTypeService.findItemTypeById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemTypeResponseDTO> updateItemType(@PathVariable Long id, @RequestBody ItemTypeDTO itemType) {
        return ResponseEntity.ok(itemTypeService.updateItemType(id, itemType));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteItemType(@PathVariable Long id) {
        itemTypeService.deleteItemType(id);
        return ResponseEntity.noContent().build();
    }
}
