package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.request.PenaltyUserRequestDTO;
import com.example.spum_backend.dto.response.PenaltyResponseDTO;
import com.example.spum_backend.service.interfaces.PenaltyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/penalties")
public class PenaltyController {

    private final PenaltyService penaltyService;

    public PenaltyController(PenaltyService penaltyService) {
        this.penaltyService = penaltyService;
    }

    @GetMapping
    public ResponseEntity<List<PenaltyResponseDTO>> getAllPenalties() {
        return ResponseEntity.ok(penaltyService.getAllPenalties());
    }

    @PostMapping
    public ResponseEntity<PenaltyResponseDTO> addPenalty(@RequestBody PenaltyRequestDTO penalty) {
        return ResponseEntity.ok(penaltyService.createPenalty(penalty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePenalty(@PathVariable Long id) {
        penaltyService.deletePenalty(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removePenaltyFromUser(@RequestBody PenaltyUserRequestDTO penalty) {
        penaltyService.removePenaltyFromUser(penalty);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<PenaltyResponseDTO> updatePenalty(@PathVariable Long id, @RequestBody PenaltyRequestDTO penalty) {
        return ResponseEntity.ok(penaltyService.updatePenalty(id, penalty));
    }

}
