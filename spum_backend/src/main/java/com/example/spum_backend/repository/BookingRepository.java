package com.example.spum_backend.repository;

import com.example.spum_backend.entity.Booking;
import com.example.spum_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentOrderByStartTimeDesc(Student student);

}
