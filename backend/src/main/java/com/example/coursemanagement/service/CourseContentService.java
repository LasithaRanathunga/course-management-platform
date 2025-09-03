package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.model.CourseContent;
import com.example.coursemanagement.repository.CourseContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseContentService {

    @Autowired
    private CourseContentRepository courseContentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseContent> getContentsByCourse(Long courseId) {
        return courseContentRepository.findByCourseId(courseId);
    }

    public CourseContent addContentToCourse(Long courseId, CourseContent content) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        content.setCourse(course);
        return courseContentRepository.save(content);
    }



    public void deleteContent(Long id) {
        courseContentRepository.deleteById(id);
    }
}
