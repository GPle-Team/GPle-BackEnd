package com.gple.backend.domain.user.repository;

import com.gple.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findAllByNameIsNotNullAndGradeIsNotNull();
}
