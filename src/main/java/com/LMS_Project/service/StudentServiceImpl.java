package com.LMS_Project.service;




import org.springframework.stereotype.Service;

import com.LMS_Project.Entity.Student;
import com.LMS_Project.dto.request.StudentRequest;
import com.LMS_Project.dto.response.StudentResponse;
import com.LMS_Project.exception.DuplicateResourceException;
import com.LMS_Project.exception.ResourceNotFoundException;
import com.LMS_Project.mapper.StudentMapper;
import com.LMS_Project.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

	 private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	 
	    private final StudentRepository studentRepository;
	    private final StudentMapper studentMapper;
	 
	    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
	        this.studentRepository = studentRepository;
	        this.studentMapper = studentMapper;
	    }
	
	
	@Override
	public StudentResponse createStudent(StudentRequest request) {
		logger.info("Creating student with email: {}", request.getEmail());
		if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Student with email " + request.getEmail() + " already exists");
        }
		 Student student = studentMapper.toEntity(request);
	        Student saved = studentRepository.save(student);
	        logger.info("Student created successfully with ID: {}", saved.getId());
	        return studentMapper.toResponse(saved);
	}

	@Override
	public StudentResponse updateStudent(Long id, StudentRequest request) {
		logger.info("Updating student with ID: {}", id);
		 
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
 
        if (!student.getEmail().equals(request.getEmail()) &&
                studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email " + request.getEmail() + " is already in use");
        }
 
        studentMapper.updateEntityFromRequest(student, request);
        Student updated = studentRepository.save(student);
        logger.info("Student updated successfully with ID: {}", updated.getId());
        return studentMapper.toResponse(updated);
	}

	@Override
	public void deleteStudent(Long id) {
		 logger.info("Deleting student with ID: {}", id);
		 
	        if (!studentRepository.existsById(id)) {
	            throw new ResourceNotFoundException("Student not found with ID: " + id);
	        }
	 
	        studentRepository.deleteById(id);
	        logger.info("Student deleted successfully with ID: {}", id);
	}

	@Override
	@Transactional(readOnly = true)
	public StudentResponse getStudentById(Long id) {
	    logger.info("Fetching student with ID: {}", id);
	    
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
 
        return studentMapper.toResponse(student);
	}

	@Override
    @Transactional(readOnly = true)
	public List<StudentResponse> getAllStudents() {
		 logger.info("Fetching all students");
		 
	        return studentRepository.findAll()
	                .stream()
	                .map(studentMapper::toResponse)
	                .collect(Collectors.toList());
	    }
	}


