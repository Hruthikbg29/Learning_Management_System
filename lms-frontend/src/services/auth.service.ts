import axiosInstance from "./axios.config";

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token:    string;
  username: string;
  role:     string;
  message:  string;
}

export interface ApiResponse<T> {
  message: string;
  data:    T;
  errors?: Record<string, string>;
}

export const authService = {
  login: async (data: LoginRequest) => {
    const res = await axiosInstance.post<ApiResponse<AuthResponse>>(
      "/api/auth/login",
      data
    );
    return res.data;
  },

  register: async (data: RegisterRequest) => {
    const res = await axiosInstance.post<ApiResponse<AuthResponse>>(
      "/api/auth/register",
      data
    );
    return res.data;
  },
};