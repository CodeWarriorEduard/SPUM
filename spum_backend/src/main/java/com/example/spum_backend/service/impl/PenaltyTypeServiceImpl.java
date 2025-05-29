package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.PenaltyTypeDTO;
import com.example.spum_backend.entity.PenaltyType;
import com.example.spum_backend.exception.notFound.PenaltyTypeNotFoundException;
import com.example.spum_backend.repository.PenaltyTypeRepository;
import com.example.spum_backend.service.interfaces.PenaltyTypeService;
import com.example.spum_backend.service.interfaces.internal.PenaltyTypeServiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PenaltyTypeServiceImpl implements PenaltyTypeService, PenaltyTypeServiceEntity {

    private final PenaltyTypeRepository penaltyTypeRepository;
    private final ModelMapper modelMapper;

    public PenaltyTypeServiceImpl(PenaltyTypeRepository penaltyTypeRepository, ModelMapper modelMapper) {
        this.penaltyTypeRepository = penaltyTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PenaltyTypeDTO addPenaltyType(PenaltyTypeDTO penaltyTypeToSave) {
        PenaltyType penaltyType = PenaltyType
                .builder()
                .penaltyType(penaltyTypeToSave.getPenaltyType())
                .penaltyDays(penaltyTypeToSave.getPenaltyDays())
                .build();

        penaltyTypeRepository.save(penaltyType);
        return penaltyTypeToSave;
    }

    @Override
    public List<PenaltyTypeDTO> getAllPenaltyTypes() {
        return penaltyTypeRepository.findAll()
                .stream().map((penaltyType -> modelMapper.map(penaltyType, PenaltyTypeDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePenaltyType(Long id) {

        penaltyTypeRepository.findById(id)
                .orElseThrow(() -> new PenaltyTypeNotFoundException("Penalty type not found"));

        penaltyTypeRepository.deleteById(id);
    }

    @Override
    public PenaltyTypeDTO getPenaltyTypeById(Long id) {

         PenaltyType penaltyType = penaltyTypeRepository.findById(id)
                .orElseThrow(() -> new PenaltyTypeNotFoundException("Penalty type not found"));

         return modelMapper.map(penaltyType, PenaltyTypeDTO.class);
    }

    @Override
    public PenaltyTypeDTO updatePenaltyType(Long id, PenaltyTypeDTO penaltyTypeDTO) {

        PenaltyType penaltyType = penaltyTypeRepository.findById(id)
                .orElseThrow(() -> new PenaltyTypeNotFoundException("Penalty type not found"));

        penaltyType.setPenaltyType(penaltyTypeDTO.getPenaltyType());
        return modelMapper.map(penaltyTypeRepository.save(penaltyType), PenaltyTypeDTO.class);
    }


    @Override
    public PenaltyType getPenaltyTypeEntityById(Long id) {
        return penaltyTypeRepository.findById(id)
                .orElseThrow(() -> new PenaltyTypeNotFoundException("Penalty type not found"));
    }
}
