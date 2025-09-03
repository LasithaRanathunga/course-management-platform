package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Result;
import com.example.coursemanagement.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public List<Result> getResultsByStudent(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }

    public List<Result> getResultsByCourse(Long courseId) {
        return resultRepository.findByCourseId(courseId);
    }

    public Result assignGrade(Result result) {
        System.out.println("##########");
        System.out.println(result);
        if (resultRepository.existsByStudent_IdAndCourse_Id(
                result.getStudent().getId(), result.getCourse().getId())) {
            throw new RuntimeException("Grade already assigned for this student in this course");
        }
        return resultRepository.save(result);
    }

    public Result updateGrade(Long id, Result updatedResult) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));
        result.setGrade(updatedResult.getGrade());
        return resultRepository.save(result);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
