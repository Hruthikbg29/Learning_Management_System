package com.LMS_Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LMS_Project.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	// Spring Security will call this to find user by email during login
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
