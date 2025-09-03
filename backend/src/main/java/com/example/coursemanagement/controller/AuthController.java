package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.*;
import com.example.coursemanagement.model.User;
import com.example.coursemanagement.model.Student;
import com.example.coursemanagement.model.Lecturer;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.repository.StudentRepository;
import com.example.coursemanagement.repository.LecturerRepository;
import com.example.coursemanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {

//        if (userRepository.existsByUsername(request.getUsername())) {
//            return ResponseEntity.badRequest().body("Username already taken");
//        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            user.setRole(User.Role.valueOf(request.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + request.getRole());
        }

        // Save user first
        User savedUser = userRepository.save(user);

        // Create corresponding entity based on role
        switch (savedUser.getRole()) {
            case STUDENT -> {
                Student student = new Student();
                student.setUser(savedUser);
                // Generate a student number (customize as you like)
                student.setStudentNumber("STU-" + savedUser.getId());
                studentRepository.save(student);
            }
            case LECTURER -> {
                Lecturer lecturer = new Lecturer();
                lecturer.setUser(savedUser);
                lecturerRepository.save(lecturer);
            }
        }

        // Generate token
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }

//    @DeleteMapping("/me")
//    public ResponseEntity<?> deleteAccount(Principal principal) {
//        String username = principal.getName();
//        userRepository.deleteByUsername(username);
//        return ResponseEntity.ok("Account deleted successfully");
//    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount(@RequestHeader("Authorization") String authHeader) {
        // Check if header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // remove "Bearer " prefix
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        String email = jwtUtil.extractEmail(token);
        userRepository.deleteByEmail(email);
        return ResponseEntity.ok("Account deleted successfully");
    }

//    @DeleteMapping("/me")
//    public ResponseEntity<?> deleteAccount(Authentication authentication) {
//        String username = authentication.getName();  // comes from JWT subject
//        userRepository.deleteByUsername(username);
//        return ResponseEntity.ok("Account deleted successfully");
//    }
}
