package com.example.spum_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "penalty_types")
@Builder
public class PenaltyType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long penaltyTypeId;

    private String penaltyType;

    private int penaltyDays;

    @OneToMany(mappedBy = "type")
    private List<Penalty> penalties;

}
