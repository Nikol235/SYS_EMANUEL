export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  id: number;
  username: string;
  role: string;
  fullName: string;
  photoUrl?: string;
}
