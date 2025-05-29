package com.example.spum_backend.controller;


import com.example.spum_backend.dto.PenaltyTypeDTO;
import com.example.spum_backend.service.interfaces.PenaltyTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/penalty-types")
public class PenaltyTypeController {

    private final PenaltyTypeService penaltyTypeService;

    public PenaltyTypeController(PenaltyTypeService penaltyTypeService) {
        this.penaltyTypeService = penaltyTypeService;
    }

    @GetMapping
    public ResponseEntity<List<PenaltyTypeDTO>> getAllPenalties() {
        return ResponseEntity.ok(penaltyTypeService.getAllPenaltyTypes());
    }

    @PostMapping
    public ResponseEntity<PenaltyTypeDTO> addPenaltyType(@RequestBody PenaltyTypeDTO itemType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(penaltyTypeService.addPenaltyType(itemType));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePenaltyType(@PathVariable Long id) {
        penaltyTypeService.deletePenaltyType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PenaltyTypeDTO> getPenaltyTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(penaltyTypeService.getPenaltyTypeById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<PenaltyTypeDTO> updatePenaltyType(@PathVariable Long id, @RequestBody PenaltyTypeDTO itemType) {
        return ResponseEntity.ok(penaltyTypeService.updatePenaltyType(id, itemType));
    }

}
