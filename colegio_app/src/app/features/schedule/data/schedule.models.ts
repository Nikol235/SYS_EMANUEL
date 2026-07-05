export interface ScheduleResponse {
  id: number;
  dayOfWeek: number;
  startTime: string;
  endTime: string;
  subject: string;
  color?: string;
  gradeLevel: { id: number; name: string; section: string };
  teacher?: { id: number; fullName: string };
}

export interface ScheduleRequest {
  gradeLevelId: number;
  dayOfWeek: number;
  startTime: string;
  endTime: string;
  subject: string;
  teacherId?: number;
  color?: string;
}
