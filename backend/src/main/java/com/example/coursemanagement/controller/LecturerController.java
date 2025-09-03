package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.model.Lecturer;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.service.CourseService;
import com.example.coursemanagement.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private CourseService courseService;

    // 🔹 Get all lecturers
    @GetMapping
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    // 🔹 Get lecturer by ID
//    @GetMapping("/{id}")
//    public Lecturer getLecturerById(@PathVariable Long id) {
//        return lecturerService.getLecturerById(id).orElseThrow(() -> new RuntimeException("Lecturer not found with id " + id));
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<Lecturer> getStudentByUserId(@PathVariable Long userId) {
        return lecturerService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Get all courses taught by a lecturer
    @GetMapping("/{id}/courses")
    public List<Course> getCoursesByLecturer(@PathVariable Long id) {
        return courseService.getCoursesByLecturer(id);
    }
}
