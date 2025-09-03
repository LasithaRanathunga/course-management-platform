package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentId(Long studentId);
    List<Result> findByCourseId(Long courseId);

    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);
}
