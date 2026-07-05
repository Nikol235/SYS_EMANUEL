package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

@Data
public class GradeResponseDTO {
    private Long id;
    private String evaluationName;
    private Double score;

    private StudentInfo student;
    private CourseInfo course;

    @Data
    public static class StudentInfo {
        private Long id;
        private String firstName;
        private String lastName;
    }

    @Data
    public static class CourseInfo {
        private Long id;
        private String name;
        private String color;
    }
}
