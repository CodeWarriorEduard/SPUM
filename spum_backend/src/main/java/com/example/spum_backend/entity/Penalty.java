package com.example.spum_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "penalties")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long penaltyId;

    private String description;
    private LocalDateTime penaltyDate;
    private LocalDateTime penaltyEndDate;

    @OneToOne
    @JoinColumn(name = "penaltyTypeId", referencedColumnName = "penaltyTypeId")
    private PenaltyType type;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Student student;

}
