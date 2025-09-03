package com.example.coursemanagement.controller;

import com.example.coursemanagement.model.CourseContent;
import com.example.coursemanagement.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-contents")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    // 🔹 Get all contents for a course
    @GetMapping("/course/{courseId}")
    public List<CourseContent> getContentsByCourse(@PathVariable Long courseId) {
        return courseContentService.getContentsByCourse(courseId);
    }

    // 🔹 Add new content to a course
    @PostMapping("/course/{courseId}")
    public CourseContent addContent(@PathVariable Long courseId, @RequestBody CourseContent content) {
        return courseContentService.addContentToCourse(courseId, content);
    }

    // 🔹 Delete a content item
    @DeleteMapping("/{contentId}")
    public void deleteContent(@PathVariable Long contentId) {
        courseContentService.deleteContent(contentId);
    }
}
