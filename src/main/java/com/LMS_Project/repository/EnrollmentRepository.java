package com.LMS_Project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.LMS_Project.Entity.Enrollment;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	List<Enrollment> findByStudentId(Long studentId);
	 
    List<Enrollment> findByCourseId(Long courseId);
 
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
 
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
 
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId AND e.status = 'ACTIVE'")
    long countActiveEnrollmentsByCourseId(Long courseId);
 
    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student JOIN FETCH e.course WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentIdWithDetails(Long studentId);

}
