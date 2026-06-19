export interface Enrollment {
  id:          number;
  studentId:   number;
  studentName: string;
  courseId:    number;
  courseTitle: string;
  status:      "ACTIVE" | "COMPLETED" | "DROPPED";
  enrolledAt:  string;
  updatedAt:   string;
}

export interface EnrollmentRequest {
  studentId: number;
  courseId:  number;
}