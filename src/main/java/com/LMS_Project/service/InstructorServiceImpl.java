package com.LMS_Project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LMS_Project.Entity.Instructor;
import com.LMS_Project.dto.request.InstructorRequest;
import com.LMS_Project.dto.response.InstructorResponse;
import com.LMS_Project.exception.DuplicateResourceException;
import com.LMS_Project.exception.ResourceNotFoundException;
import com.LMS_Project.mapper.InstructorMapper;
import com.LMS_Project.repository.InstructorRepository;



@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

	private static final Logger logger = LoggerFactory.getLogger(InstructorServiceImpl.class);

	private final InstructorRepository instructorRepository;
	private final InstructorMapper instructorMapper;

	public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorMapper instructorMapper) {
		this.instructorRepository = instructorRepository;
		this.instructorMapper = instructorMapper;
	}

	@Override
	public InstructorResponse createInstructor(InstructorRequest request) {
		logger.info("Creating instructor with email: {}", request.getEmail());

		if (instructorRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Instructor with email " + request.getEmail() + " already exists");
		}

		Instructor instructor = instructorMapper.toEntity(request);
		Instructor saved = instructorRepository.save(instructor);
		logger.info("Instructor created successfully with ID: {}", saved.getId());
		return instructorMapper.toResponse(saved);
	}

	@Override
	public InstructorResponse updateInstructor(Long id, InstructorRequest request) {
		logger.info("Updating instructor with ID: {}", id);

		Instructor instructor = instructorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + id));

		if (!instructor.getEmail().equals(request.getEmail())
				&& instructorRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email " + request.getEmail() + " is already in use");
		}

		instructorMapper.updateEntityFromRequest(instructor, request);
		Instructor updated = instructorRepository.save(instructor);
		logger.info("Instructor updated successfully with ID: {}", updated.getId());
		return instructorMapper.toResponse(updated);

	}

	@Override
	public void deleteInstructor(Long id) {
	    logger.info("Deleting instructor with ID: {}", id);
	    
        if (!instructorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instructor not found with ID: " + id);
        }
 
        instructorRepository.deleteById(id);
        logger.info("Instructor deleted successfully with ID: {}", id);
 
	}

	@Override
	 @Transactional(readOnly  = true)
	public InstructorResponse getInstructorById(Long id) {
		logger.info("Fetching instructor with ID: {}", id);
		 
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + id));
 
        return instructorMapper.toResponse(instructor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstructorResponse> getAllInstructors() {
		logger.info("Fetching all instructors");
		 
        return instructorRepository.findAll()
                .stream()
                .map(instructorMapper::toResponse)
                .collect(Collectors.toList());
	}

}
