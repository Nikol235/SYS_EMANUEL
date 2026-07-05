package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

@Data
public class TeacherCourseResponseDTO {
    private Long id;
    private String evalNames;

    private TeacherInfo teacher;
    private CourseInfo course;
    private GradeLevelInfo gradeLevel;

    @Data
    public static class TeacherInfo {
        private Long id;
        private String fullName;
    }

    @Data
    public static class CourseInfo {
        private Long id;
        private String name;
        private String color;
    }

    @Data
    public static class GradeLevelInfo {
        private Long id;
        private String name;
        private String section;
    }
}
