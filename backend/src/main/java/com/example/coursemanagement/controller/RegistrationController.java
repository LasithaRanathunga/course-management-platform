package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Registration;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.service.RegistrationService;
import com.example.coursemanagement.service.StudentService;
import com.example.coursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private StudentService studentService;  // to fetch student by ID

    @Autowired
    private CourseService courseService;    // to fetch course by ID

    // Student registers for a course
    @PostMapping("/enroll")
    public Registration enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        Student student = studentService.getStudentById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return registrationService.registerStudent(student, course);
    }

    // Student drops a course
    @DeleteMapping("/{registrationId}")
    public String dropRegistration(@PathVariable Long registrationId) {
        registrationService.dropRegistration(registrationId);
        return "Registration dropped successfully";
    }

    // Get all registrations for a student
    @GetMapping("/student/{studentId}")
    public List<Registration> getRegistrationsByStudent(@PathVariable Long studentId) {
        return registrationService.getRegistrationsByStudent(studentId);
    }

    // Get all registrations for a course
    @GetMapping("/course/{courseId}")
    public List<Registration> getRegistrationsByCourse(@PathVariable Long courseId) {
        return registrationService.getRegistrationsByCourse(courseId);
    }
}
