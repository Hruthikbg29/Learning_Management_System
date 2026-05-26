package com.LMS_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LMS_Project.Entity.Student;

import java.util.Optional;
 
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<com.LMS_Project.Entity.Student> findByEmail(String email);
    boolean existsByEmail(String email);
}
