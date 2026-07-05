export interface UserResponse {
  id: number;
  username: string;
  role: string;
  fullName?: string;
  dni?: string;
  email?: string;
  phone?: string;
  photoUrl?: string;
  active: boolean;
  createdAt?: string;
}

export interface UserRequest {
  username?: string;
  password?: string;
  role?: string;
  fullName?: string;
  dni?: string;
  email?: string;
  phone?: string;
  photoUrl?: string;
  active?: boolean;
}
