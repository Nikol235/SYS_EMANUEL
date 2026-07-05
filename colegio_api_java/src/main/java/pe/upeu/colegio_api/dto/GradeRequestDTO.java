package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeRequestDTO {

    @NotNull(message = "El alumno es requerido")
    private Long studentId;

    @NotNull(message = "La asignación de curso es requerida")
    private Long teacherCourseId;

    @NotBlank(message = "El nombre de la evaluación es requerido")
    private String evaluationName;

    @NotNull(message = "La nota es requerida")
    private Double score;
}
