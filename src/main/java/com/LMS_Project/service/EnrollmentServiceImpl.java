package com.LMS_Project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LMS_Project.Entity.Course;
import com.LMS_Project.Entity.Enrollment;
import com.LMS_Project.Entity.Enrollment.EnrollmentStatus;
import com.LMS_Project.Entity.Student;
import com.LMS_Project.dto.request.EnrollmentRequest;
import com.LMS_Project.dto.response.EnrollmentResponse;
import com.LMS_Project.exception.BadRequestException;
import com.LMS_Project.exception.DuplicateResourceException;
import com.LMS_Project.exception.ResourceNotFoundException;
import com.LMS_Project.mapper.EnrollmentMapper;
import com.LMS_Project.repository.CourseRepository;
import com.LMS_Project.repository.EnrollmentRepository;
import com.LMS_Project.repository.StudentRepository;

import jakarta.websocket.server.ServerEndpoint;



@Service
public class EnrollmentServiceImpl implements EnrollmentService {
	private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
	 
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;
 
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

	@Override
	public EnrollmentResponse enrollStudent(EnrollmentRequest request) {
		logger.info("Enrolling student ID: {} into course ID: {}", request.getStudentId(), request.getCourseId());
		 
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with ID: " + request.getStudentId()));
 
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with ID: " + request.getCourseId()));
 
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new DuplicateResourceException("Student is already enrolled in this course");
        }
 
        long activeEnrollments = enrollmentRepository.countActiveEnrollmentsByCourseId(request.getCourseId());
        if (course.getMaxStudents() != null && activeEnrollments >= course.getMaxStudents()) {
            throw new BadRequestException("Course has reached its maximum enrollment capacity of " + course.getMaxStudents());
        }
 
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .build();
 
        Enrollment saved = enrollmentRepository.save(enrollment);
        logger.info("Student enrolled successfully. Enrollment ID: {}", saved.getId());
        return enrollmentMapper.toResponse(saved);
    }

	@Override
	public EnrollmentResponse updateEnrollmentStatus(Long enrollmentId, EnrollmentStatus status) {
		logger.info("Updating enrollment ID: {} to status: {}", enrollmentId, status);
		 
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with ID: " + enrollmentId));
 
        enrollment.setStatus(status);
        Enrollment updated = enrollmentRepository.save(enrollment);
        logger.info("Enrollment status updated successfully for ID: {}", enrollmentId);
        return enrollmentMapper.toResponse(updated);
	}

	@Override
	public void cancelEnrollment(Long enrollmentId) {
		logger.info("Cancelling enrollment ID: {}", enrollmentId);
		 
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with ID: " + enrollmentId));
 
        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollmentRepository.save(enrollment);
        logger.info("Enrollment cancelled (DROPPED) for ID: {}", enrollmentId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnrollmentResponse> getEnrollmentsByStudentId(Long studentId) {
		 logger.info("Fetching enrollments for student ID: {}", studentId);
		 
	        if (!studentRepository.existsById(studentId)) {
	            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
	        }
	 
	        return enrollmentRepository.findByStudentIdWithDetails(studentId)
	                .stream()
	                .map(enrollmentMapper::toResponse)
	                .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnrollmentResponse> getEnrollmentsByCourseId(Long courseId) {
		logger.info("Fetching enrollments for course ID: {}", courseId);
		 
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with ID: " + courseId);
        }
 
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnrollmentResponse> getCoursesByStudentId(Long studentId) {
		logger.info("Fetching courses for student ID: {}", studentId);
		 
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
 
        return enrollmentRepository.findByStudentIdWithDetails(studentId)
                .stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<EnrollmentResponse> getAllEnrollments() {
		   logger.info("Fetching all enrollments");
		   
	        return enrollmentRepository.findAll()
	                .stream()
	                .map(enrollmentMapper::toResponse)
	                .collect(Collectors.toList());
	}
}
