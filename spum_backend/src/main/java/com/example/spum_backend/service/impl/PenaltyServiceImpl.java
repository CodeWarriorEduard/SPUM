package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.request.PenaltyUserRequestDTO;
import com.example.spum_backend.dto.response.PenaltyResponseDTO;
import com.example.spum_backend.entity.Penalty;
import com.example.spum_backend.entity.PenaltyType;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.exception.notFound.PenaltyNotFoundException;
import com.example.spum_backend.mapper.PenaltyMapper;
import com.example.spum_backend.repository.PenaltyRepository;
import com.example.spum_backend.service.interfaces.PenaltyService;
import com.example.spum_backend.service.interfaces.internal.PenaltyServiceEntity;
import com.example.spum_backend.service.interfaces.internal.PenaltyTypeServiceEntity;
import com.example.spum_backend.service.interfaces.internal.StudentServiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PenaltyServiceImpl implements PenaltyService, PenaltyServiceEntity {

    private final PenaltyRepository penaltyRepository;
    private final StudentServiceEntity studentServiceEntity;
    private final PenaltyTypeServiceEntity penaltyTypeServiceEntity;
    private final PenaltyMapper penaltyMapper;
    private final PenaltyTypeServiceImpl penaltyTypeServiceImpl;


    public PenaltyServiceImpl(PenaltyRepository penaltyRepository, StudentServiceEntity studentServiceEntity, PenaltyTypeServiceEntity penaltyTypeServiceEntity, PenaltyMapper penaltyMapper, PenaltyTypeServiceImpl penaltyTypeServiceImpl) {
        this.penaltyRepository = penaltyRepository;
        this.studentServiceEntity = studentServiceEntity;
        this.penaltyTypeServiceEntity = penaltyTypeServiceEntity;
        this.penaltyMapper = penaltyMapper;
        this.penaltyTypeServiceImpl = penaltyTypeServiceImpl;
    }

    @Override
    public List<PenaltyResponseDTO> getAllPenalties() {
        return penaltyRepository.findAll()
                .stream().map(penaltyMapper::toDTO).toList();
    }

    @Override
    public PenaltyResponseDTO createPenalty(PenaltyRequestDTO penaltyRequestDTO) {

        // Verify a student exists in our db
        // Use email or student id.
        Student student = studentServiceEntity.findStudentByEmail(penaltyRequestDTO.getEmail());

        PenaltyType penaltyType = penaltyTypeServiceEntity.getPenaltyTypeEntityById(penaltyRequestDTO.getPenaltyType());

        // depending on the penalty type different times

        // Applying the penalty.

        Penalty penalty = Penalty.builder()
                .penaltyDate(penaltyRequestDTO.getPenaltyDate())
                .penaltyEndDate(penaltyRequestDTO.getPenaltyDate().plusDays(penaltyType.getPenaltyDays()))
                .description(penaltyRequestDTO.getDescription())
                .type(penaltyType)
                .student(student)
                .build();

        return penaltyMapper.toDTO(penaltyRepository.save(penalty));
    }

    @Override
    public void deletePenalty(Long id) {
        penaltyRepository.delete(getPenaltyById(id));
    }

    @Override
    public void removePenaltyFromUser(PenaltyUserRequestDTO penaltyUserRequestDTO) {

        Student student = studentServiceEntity.findStudentByEmail(penaltyUserRequestDTO.getEmail());
        Penalty penalty = getPenaltyById(penaltyUserRequestDTO.getPenaltyId());

        student.getPenalties().remove(penalty);
        penalty.setStudent(null);

        penaltyRepository.delete(penalty);
    }

    @Override
    public PenaltyResponseDTO updatePenalty(Long id, PenaltyRequestDTO penaltyRequestDTO) {
        Penalty penaltyToUpdate = getPenaltyById(id);

        penaltyToUpdate.setDescription
                (penaltyRequestDTO.getDescription()==null?penaltyToUpdate.getDescription():penaltyRequestDTO.getDescription());
        penaltyToUpdate.setPenaltyDate
                (penaltyRequestDTO.getPenaltyDate()==null?penaltyToUpdate.getPenaltyDate():penaltyRequestDTO.getPenaltyDate());
        penaltyToUpdate.setType
                (penaltyRequestDTO.getPenaltyType()!=null?penaltyTypeServiceImpl.getPenaltyTypeEntityById(penaltyRequestDTO.getPenaltyType()):penaltyToUpdate.getType());
        penaltyToUpdate.setStudent
                (penaltyRequestDTO.getEmail()!=null?studentServiceEntity.findStudentByEmail(penaltyRequestDTO.getEmail()):penaltyToUpdate.getStudent());

        return penaltyMapper.toDTO(penaltyRepository.save(penaltyToUpdate));
    }


    @Override
    public Penalty getPenaltyById(Long id) {
        return penaltyRepository.findById(id)
                .orElseThrow(() -> new PenaltyNotFoundException("Penalty Not Found"));
    }

    @Override
    public Penalty savePenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    @Override
    public Optional<Penalty> findActivePenalty(Long id, LocalDateTime startTime) {
        return penaltyRepository.findActivePenalizations(id, startTime);
    }

    @Override
    public List<Penalty> findAllPenalties() {
        return penaltyRepository.findAll();
    }
}
