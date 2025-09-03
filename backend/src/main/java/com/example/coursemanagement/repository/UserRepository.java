package com.example.coursemanagement.repository;

import com.example.coursemanagement.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);  // ✅ Add this

    boolean existsByEmail(String email);        // (optional but useful)

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    void deleteByEmail(String email);

    @Transactional
    void deleteById(Long id);
}
