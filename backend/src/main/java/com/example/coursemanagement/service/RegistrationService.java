package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Registration;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.model.Course;
import com.example.coursemanagement.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    // Get all registrations for a student
    public List<Registration> getRegistrationsByStudent(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }

    // Get all registrations for a course
    public List<Registration> getRegistrationsByCourse(Long courseId) {
        return registrationRepository.findByCourseId(courseId);
    }

    // Register student for a course
    public Registration registerStudent(Student student, Course course) {
        if (registrationRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
            throw new RuntimeException("Student already registered for this course");
        }

        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    // Drop registration
    public void dropRegistration(Long id) {
        registrationRepository.deleteById(id);
    }
}
