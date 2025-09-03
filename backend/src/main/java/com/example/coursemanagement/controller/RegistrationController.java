package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Registration;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.security.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    // Student registers for a course
    @PostMapping("/enroll")
    public Registration enrollStudent(@RequestParam Long courseId, @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        Long id = jwtUtil.extractUserId(token);


        System.out.println(id);

        Student student = studentService.findByUserId(id).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return registrationService.registerStudent(student, course);
    }

//    @PostMapping("/enroll")
//    public void enrollStudent(@RequestParam Long courseId, @RequestHeader("Authorization") String authHeader) {
//        System.out.println("############");
//    }


    // Student drops a course
    @DeleteMapping("/{registrationId}")
    public String dropRegistration(@PathVariable Long registrationId) {
        registrationService.dropRegistration(registrationId);
        return "Registration dropped successfully";
    }

    // Get all registrations for a student
    @GetMapping("/student/allCourses")
    public List<Registration> getRegistrationsByStudent(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        Long id = jwtUtil.extractUserId(token);

        Student student = studentService.findByUserId(id).orElseThrow(() -> new RuntimeException("Student not found"));

        return registrationService.getRegistrationsByStudent(student.getId());
    }

    // Get all registrations for a course
    @GetMapping("/course/{courseId}")
    public List<Registration> getRegistrationsByCourse(@PathVariable Long courseId) {
        return registrationService.getRegistrationsByCourse(courseId);
    }
}
