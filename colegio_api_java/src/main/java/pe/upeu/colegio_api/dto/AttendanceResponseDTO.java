package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceResponseDTO {
    private Long id;
    private LocalDate date;
    private String status;
    private String turno;
    private String tipo;
    private LocalDateTime updatedAt;

    private StudentInfo student;

    @Data
    public static class StudentInfo {
        private Long id;
        private String firstName;
        private String lastName;
    }
}
