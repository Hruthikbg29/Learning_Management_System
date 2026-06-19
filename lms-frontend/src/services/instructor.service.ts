import axiosInstance from "./axios.config";

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

interface ApiResponse<T> {
  message: string;
  data:    T;
  errors?: Record<string, string>;
}

export const instructorService = {
  getAll: () =>
    axiosInstance.get<ApiResponse<Instructor[]>>("/api/instructors"),

  getById: (id: number) =>
    axiosInstance.get<ApiResponse<Instructor>>(`/api/instructors/${id}`),

  create: (data: InstructorRequest) =>
    axiosInstance.post<ApiResponse<Instructor>>("/api/instructors", data),

  update: (id: number, data: InstructorRequest) =>
    axiosInstance.put<ApiResponse<Instructor>>(`/api/instructors/${id}`, data),

  delete: (id: number) =>
    axiosInstance.delete(`/api/instructors/${id}`),
};