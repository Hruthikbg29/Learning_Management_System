package com.LMS_Project.repository;

import com.LMS_Project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.LMS_Project.Entity.User, Long> {
    Optional<com.LMS_Project.Entity.User> findByUsername(String username);
    boolean existsByUsername(String username);
}