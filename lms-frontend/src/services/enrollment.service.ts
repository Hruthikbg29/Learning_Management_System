import axiosInstance from "./axios.config";

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

interface ApiResponse<T> {
  message: string;
  data:    T;
  errors?: Record<string, string>;
}

export const enrollmentService = {
  getAll: () =>
    axiosInstance.get<ApiResponse<Enrollment[]>>("/api/enrollments"),

  enroll: (data: EnrollmentRequest) =>
    axiosInstance.post<ApiResponse<Enrollment>>("/api/enrollments", data),

  getByStudent: (id: number) =>
    axiosInstance.get<ApiResponse<Enrollment[]>>(
      `/api/enrollments/student/${id}/courses`
    ),

  getByCourse: (id: number) =>
    axiosInstance.get<ApiResponse<Enrollment[]>>(
      `/api/enrollments/course/${id}`
    ),

  updateStatus: (id: number, status: string) =>
    axiosInstance.patch(
      `/api/enrollments/${id}/status?status=${status}`
    ),

  cancel: (id: number) =>
    axiosInstance.delete(`/api/enrollments/${id}`),
};