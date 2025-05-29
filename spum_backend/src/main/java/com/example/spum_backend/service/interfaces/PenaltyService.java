package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.request.PenaltyUserRequestDTO;
import com.example.spum_backend.dto.response.PenaltyResponseDTO;
import com.example.spum_backend.entity.Penalty;

import java.util.List;

public interface PenaltyService {
    List<PenaltyResponseDTO> getAllPenalties();
    PenaltyResponseDTO createPenalty(PenaltyRequestDTO penaltyRequestDTO);
    void deletePenalty(Long id);
    void removePenaltyFromUser(PenaltyUserRequestDTO penaltyUserRequestDTO);
    PenaltyResponseDTO updatePenalty(Long id, PenaltyRequestDTO penaltyRequestDTO);
}
