package com.example.coursemanagement.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CourseContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private ContentType type;

    private String urlOrPath;

    public enum ContentType {
        PDF, VIDEO, OTHER
    }
}
