export interface PaginationModel<T> {
  content: T;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
