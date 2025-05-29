package com.example.spum_backend.repository;

import com.example.spum_backend.entity.Penalty;
import com.example.spum_backend.entity.PenaltyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    @Query("select p from Penalty p join Student s on s.studentId = p.student.studentId join User u on u.id = s.user.id where u.id =:idUser and p.penaltyEndDate >=:bookingStartDate")
    Optional<Penalty> findActivePenalizations(Long idUser, LocalDateTime bookingStartDate);

}
