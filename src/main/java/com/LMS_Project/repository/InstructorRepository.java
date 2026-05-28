package com.LMS_Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LMS_Project.Entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	Optional<Instructor> findByEmail(String email);
    boolean existsByEmail(String email);
}
