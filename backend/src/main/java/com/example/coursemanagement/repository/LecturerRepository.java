package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Lecturer;
import com.example.coursemanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LecturerRepository extends JpaRepository<Lecturer, Long>{
    Optional<Lecturer> findByUserId(Long userId);
}
