package com.example.coursemanagement.service;

import com.example.coursemanagement.model.Lecturer;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public Optional<Lecturer> getLecturerById(Long id) {
        return lecturerRepository.findById(id);
    }

    public Optional<Lecturer> findByUserId(Long id) {
        return lecturerRepository.findByUserId(id);
    }

    public Lecturer createLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    public void deleteLecturer(Long id) {
        lecturerRepository.deleteById(id);
    }
}

