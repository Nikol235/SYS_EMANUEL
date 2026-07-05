export interface AttendanceResponse {
  id: number;
  date: string;
  status: string;
  turno: string;
  tipo: string;
  updatedAt?: string;
  student: { id: number; firstName: string; lastName: string };
}

export interface AttendanceRequest {
  studentId: number;
  date: string;
  status: string;
  turno?: string;
  tipo?: string;
}
