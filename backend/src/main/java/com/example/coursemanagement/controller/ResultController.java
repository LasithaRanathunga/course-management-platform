package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Result;
import com.example.coursemanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    // 🔹 Assign a grade to a student for a course
    @PostMapping
    public Result assignGrade(@RequestBody Result result) {

        System.out.println("###############");
        return resultService.assignGrade(result);
    }

    // 🔹 Update grade
    @PutMapping("/{id}")
    public Result updateGrade(@PathVariable Long id, @RequestBody Result result) {
        return resultService.updateGrade(id, result);
    }

    // 🔹 Get all results for a student (transcript)
    @GetMapping("/student/{studentId}")
    public List<Result> getResultsByStudent(@PathVariable Long studentId) {
        return resultService.getResultsByStudent(studentId);
    }

    // 🔹 Get all results for a course
    @GetMapping("/course/{courseId}")
    public List<Result> getResultsByCourse(@PathVariable Long courseId) {
        return resultService.getResultsByCourse(courseId);
    }

    // 🔹 Delete a result
    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
    }
}
