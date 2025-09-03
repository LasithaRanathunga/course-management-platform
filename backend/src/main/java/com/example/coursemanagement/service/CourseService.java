package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.model.Lecturer;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesByLecturer(Long lecturerId) {
        return courseRepository.findByLecturerId(lecturerId);
    }

//    public Course createCourse(Course course) {
//        return courseRepository.save(course);
//    }

    public Course createCourse(Course course, Long userId) {
        // Find lecturer using userId from JWT
        Lecturer lecturer = lecturerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Lecturer not found for userId: " + userId));

        course.setLecturer(lecturer);
        return courseRepository.save(course);
    }


    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {
            course.setCode(updatedCourse.getCode());
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            course.setCredits(updatedCourse.getCredits());
            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
