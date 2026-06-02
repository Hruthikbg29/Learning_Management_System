package com.LMS_Project.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "student_id", nullable = false)
	    private Student student;
	 
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "course_id", nullable = false)
	    private Course course;
	 
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private EnrollmentStatus status;
	 
	    @Column(name = "enrolled_at")
	    private LocalDateTime enrolledAt;
	 
	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	 
	    public enum EnrollmentStatus {
	        ACTIVE, COMPLETED, DROPPED
	    }
	 
	    @PrePersist
	    protected void onCreate() {
	        enrolledAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	        if (status == null) status = EnrollmentStatus.ACTIVE;
	    }
	 
	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
}
