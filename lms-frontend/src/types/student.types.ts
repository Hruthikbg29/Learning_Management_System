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