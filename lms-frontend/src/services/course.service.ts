import axiosInstance from "./axios.config";

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

interface ApiResponse<T> {
  message: string;
  data:    T;
  errors?: Record<string, string>;
}

export const courseService = {
  getAll: () =>
    axiosInstance.get<ApiResponse<Course[]>>("/api/courses"),

  getById: (id: number) =>
    axiosInstance.get<ApiResponse<Course>>(`/api/courses/${id}`),

  create: (data: CourseRequest) =>
    axiosInstance.post<ApiResponse<Course>>("/api/courses", data),

  update: (id: number, data: CourseRequest) =>
    axiosInstance.put<ApiResponse<Course>>(`/api/courses/${id}`, data),

  delete: (id: number) =>
    axiosInstance.delete(`/api/courses/${id}`),

  search: (keyword: string) =>
    axiosInstance.get<ApiResponse<Course[]>>(
      `/api/courses/search?keyword=${keyword}`
    ),

  getByInstructor: (id: number) =>
    axiosInstance.get<ApiResponse<Course[]>>(
      `/api/courses/instructor/${id}`
    ),
};