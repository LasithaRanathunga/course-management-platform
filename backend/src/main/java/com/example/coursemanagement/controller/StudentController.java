package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.model.Registration;
import com.example.coursemanagement.model.Result;
import com.example.coursemanagement.service.StudentService;
import com.example.coursemanagement.service.RegistrationService;
import com.example.coursemanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ResultService resultService;

    // 🔹 Get all students (lecturer/admins can see all students)
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // 🔹 Get student by ID
//    @GetMapping("/{id}")
//    public Student getStudentById(@PathVariable Long id) {
//        return studentService.getStudentById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<Student> getStudentByUserId(@PathVariable Long userId) {
        return studentService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Get all courses registered by student
    @GetMapping("/{id}/courses")
    public List<Registration> getCoursesByStudent(@PathVariable Long id) {
        return registrationService.getRegistrationsByStudent(id);
    }

    // 🔹 Get all results (transcript) for a student
    @GetMapping("/{id}/results")
    public List<Result> getResultsByStudent(@PathVariable Long id) {
        return resultService.getResultsByStudent(id);
    }
}
