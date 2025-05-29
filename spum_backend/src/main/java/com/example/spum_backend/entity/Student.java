package com.example.spum_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
@Builder
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;

    private Long studentCollegeId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Penalty> penalties;

    @Override
    public String toString() {
        return "Student{" +
                "user=" + user.getUserFirstName() +
                '}';
    }
}
