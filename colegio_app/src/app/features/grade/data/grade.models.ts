export interface GradeResponse {
  id: number;
  evaluationName: string;
  score?: number;
  student: { id: number; firstName: string; lastName: string };
  course: { id: number; name: string; color: string };
}

export interface GradeRequest {
  studentId: number;
  teacherCourseId: number;
  evaluationName: string;
  score?: number;
}
