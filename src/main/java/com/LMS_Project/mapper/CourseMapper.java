package com.LMS_Project.mapper;

import org.springframework.stereotype.Component;

import com.LMS_Project.Entity.Course;
import com.LMS_Project.dto.request.CourseRequest;
import com.LMS_Project.dto.response.CourseResponse;

@Component
public class CourseMapper {


    public Course toEntity(CourseRequest request) {
        return Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .maxStudents(request.getMaxStudents())
                .build();
    }
 
    public CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .duration(course.getDuration())
                .maxStudents(course.getMaxStudents())
                .instructorId(course.getInstructor() != null ? course.getInstructor().getId() : null)
                .instructorName(course.getInstructor() != null ? course.getInstructor().getName() : null)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
 
    public void updateEntityFromRequest(Course course, CourseRequest request) {
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setDuration(request.getDuration());
        course.setMaxStudents(request.getMaxStudents());
    }
}
