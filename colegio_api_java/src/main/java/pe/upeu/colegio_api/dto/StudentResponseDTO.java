package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private LocalDate birthDate;
    private GradeLevelInfo gradeLevel;
    private String codigo;
    private Boolean active;
    private String photoUrl;
    private String parentPhone;
    private Long parentId;
    private String parentUsername;

    @Data
    public static class GradeLevelInfo {
        private Long id;
        private String name;
        private String section;
    }
}
