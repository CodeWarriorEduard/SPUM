package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.response.PenaltyResponseDTO;
import com.example.spum_backend.entity.Penalty;
import com.example.spum_backend.service.interfaces.PenaltyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/penalties")
public class PenaltyController {

    private final PenaltyService penaltyService;

    public PenaltyController(PenaltyService penaltyService) {
        this.penaltyService = penaltyService;
    }

    @PostMapping("/add")
    public void addPenalty(@RequestBody PenaltyRequestDTO penalty) {
        penaltyService.createPenalty(penalty);
    }


    @GetMapping("/all")
    public List<PenaltyResponseDTO> getAllPenalties() {
        return penaltyService.getAllPenalties();
    }

}
