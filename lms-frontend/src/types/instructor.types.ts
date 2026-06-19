export interface Instructor {
  id:             number;
  name:           string;
  email:          string;
  phone:          string;
  specialization: string;
  createdAt:      string;
  updatedAt:      string;
}

export interface InstructorRequest {
  name:           string;
  email:          string;
  phone:          string;
  specialization: string;
}