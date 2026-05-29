package com.LMS_Project.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Course {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	    @Column(nullable = false)
	    private String title;
	 
	    @Column(length = 1000)
	    private String description;
	 
	    @Column(nullable = false)
	    private String duration;
	 
	    @Column(name = "max_students")
	    private Integer maxStudents;
	 
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "instructor_id")
	    private Instructor instructor;
	 
	    @Column(name = "created_at")
	    private LocalDateTime createdAt;
	 
	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	 
//	    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	    private List<Enrollment> enrollments;
	 
	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }
	 
	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
}
