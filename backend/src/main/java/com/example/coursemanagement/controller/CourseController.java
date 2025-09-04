package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.model.CourseContent;
import com.example.coursemanagement.repository.LecturerRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.CourseService;
import com.example.coursemanagement.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.coursemanagement.security.JwtUtil;


import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseContentService courseContentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private JwtUtil jwtUtil;


    // 🔹 Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // 🔹 Get course by ID
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id).orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    // 🔹 Create a new course (lecturer/admin)
//    @PostMapping
//    public Course createCourse(@RequestBody Course course) {
//        return courseService.createCourse(course);
//    }

    @PostMapping
    public Course createCourse(@RequestBody Course course, @RequestHeader("Authorization") String authHeader) {

        // Check if header is present and starts with "Bearer "
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
//        }

        String token = authHeader.substring(7); // remove "Bearer " prefix
//        if (!jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(401).body("Invalid or expired token");
//        }

        String email = jwtUtil.extractEmail(token);

        // Get userId from repository
        System.out.println(email);
        Long userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        Course savedCourse = courseService.createCourse(course, userId);


        return savedCourse;
    }

    // 🔹 Update course details (lecturer/admin)
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    // 🔹 Delete a course
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/by-lecturer")
    public List<Course> getCoursesByLecturer(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        Long userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        Long lecturerId = lecturerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        System.out.println(lecturerId);

        return courseService.getCoursesByLecturer(lecturerId);
    }

    // 🔹 Get all content for a course
    @GetMapping("/{id}/contents")
    public List<CourseContent> getCourseContents(@PathVariable Long id) {
        return courseContentService.getContentsByCourse(id);
    }

    // 🔹 Add content to a course (PDF/Video)
    @PostMapping("/{id}/contents")
    public CourseContent addCourseContent(@PathVariable Long id, @RequestBody CourseContent content) {
        return courseContentService.addContentToCourse(id, content);
    }

    // 🔹 Delete content from a course
    @DeleteMapping("/contents/{contentId}")
    public void deleteCourseContent(@PathVariable Long contentId) {
        courseContentService.deleteContent(contentId);
    }
}
