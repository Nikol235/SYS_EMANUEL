package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunicationResponseDTO {
    private Long id;
    private String title;
    private String body;
    private String type;
    private String attachments;
    private String studentIds;
    private LocalDateTime createdAt;

    private AuthorInfo author;
    private CourseInfo course;
    private GradeLevelInfo gradeLevel;

    @Data
    public static class AuthorInfo {
        private Long id;
        private String fullName;
        private String role;
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
    }
}
