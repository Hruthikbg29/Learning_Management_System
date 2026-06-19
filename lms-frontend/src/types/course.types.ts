export interface Course {
  id:             number;
  title:          string;
  description:    string;
  duration:       string;
  maxStudents:    number;
  instructorId:   number | null;
  instructorName: string | null;
  createdAt:      string;
  updatedAt:      string;
}

export interface CourseRequest {
  title:         string;
  description:   string;
  duration:      string;
  maxStudents:   number;
  instructorId?: number;
}