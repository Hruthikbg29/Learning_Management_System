package com.LMS_Project.service;



import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LMS_Project.Entity.Course;
import com.LMS_Project.Entity.Instructor;
import com.LMS_Project.dto.request.CourseRequest;
import com.LMS_Project.dto.response.CourseResponse;
import com.LMS_Project.exception.ResourceNotFoundException;
import com.LMS_Project.mapper.CourseMapper;
import com.LMS_Project.repository.CourseRepository;
import com.LMS_Project.repository.InstructorRepository;


@Service
@Transactional
public class CourseServiceImpl implements CourseService{

	private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
	 
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseMapper courseMapper;
 
    public CourseServiceImpl(CourseRepository courseRepository,
                             InstructorRepository instructorRepository,
                             CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.courseMapper = courseMapper;
    }

	@Override
	public CourseResponse createCourse(CourseRequest request) {
		logger.info("Creating course with title: {}", request.getTitle());
		 
        Course course = courseMapper.toEntity(request);
 
        if (request.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Instructor not found with ID: " + request.getInstructorId()));
            course.setInstructor(instructor);
        }
 
        Course saved = courseRepository.save(course);
        logger.info("Course created successfully with ID: {}", saved.getId());
        return courseMapper.toResponse(saved);
	}

	@Override
	public CourseResponse updateCourse(Long id, CourseRequest request) {
		logger.info("Updating course with ID: {}", id);
		 
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
 
        courseMapper.updateEntityFromRequest(course, request);
 
        if (request.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Instructor not found with ID: " + request.getInstructorId()));
            course.setInstructor(instructor);
        } else {
            course.setInstructor(null);
        }
 
        Course updated = courseRepository.save(course);
        logger.info("Course updated successfully with ID: {}", updated.getId());
        return courseMapper.toResponse(updated);
	}

	@Override
	public void deleteCourse(Long id) {
		logger.info("Deleting course with ID: {}", id);
		 
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with ID: " + id);
        }
 
        courseRepository.deleteById(id);
        logger.info("Course deleted successfully with ID: {}", id);
		
	}

	@Override
	 @Transactional(readOnly = true)
	public CourseResponse getCourseById(Long id) {
		logger.info("Fetching course with ID: {}", id);
		 
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
 
        return courseMapper.toResponse(course);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CourseResponse> getAllCourses() {
		logger.info("Fetching all courses");
		 
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<CourseResponse> getCoursesByInstructorId(Long instructorId) {
		logger.info("Fetching courses for instructor ID: {}", instructorId);
		 
        if (!instructorRepository.existsById(instructorId)) {
            throw new ResourceNotFoundException("Instructor not found with ID: " + instructorId);
        }
 
        return courseRepository.findByInstructorId(instructorId)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<CourseResponse> searchCourses(String keyword) {
		 logger.info("Searching courses with keyword: {}", keyword);
		 
	        return courseRepository.searchByKeyword(keyword)
	                .stream()
	                .map(courseMapper::toResponse)
	                .collect(Collectors.toList());
	}
    
    

   
}
