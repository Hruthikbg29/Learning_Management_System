import axiosInstance from "./axios.config";

export interface Student {
  id:        number;
  name:      string;
  email:     string;
  phone:     string;
  createdAt: string;
  updatedAt: string;
}

export interface StudentRequest {
  name:  string;
  email: string;
  phone: string;
}

interface ApiResponse<T> {
  message: string;
  data:    T;
  errors?: Record<string, string>;
}

export const studentService = {
  getAll: () =>
    axiosInstance.get<ApiResponse<Student[]>>("/api/students"),

  getById: (id: number) =>
    axiosInstance.get<ApiResponse<Student>>(`/api/students/${id}`),

  create: (data: StudentRequest) =>
    axiosInstance.post<ApiResponse<Student>>("/api/students", data),

  update: (id: number, data: StudentRequest) =>
    axiosInstance.put<ApiResponse<Student>>(`/api/students/${id}`, data),

  delete: (id: number) =>
    axiosInstance.delete(`/api/students/${id}`),
};



