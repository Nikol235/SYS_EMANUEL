export interface CourseResponse {
  id: number;
  name: string;
  color: string;
  description?: string;
}

export interface CourseRequest {
  name: string;
  color?: string;
  description?: string;
}
