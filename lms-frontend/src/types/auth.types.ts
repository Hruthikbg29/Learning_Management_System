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