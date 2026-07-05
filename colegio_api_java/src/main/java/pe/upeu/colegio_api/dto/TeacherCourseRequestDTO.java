package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherCourseRequestDTO {

    @NotNull(message = "El docente es requerido")
    private Long teacherId;

    @NotNull(message = "El curso es requerido")
    private Long courseId;

    @NotNull(message = "El grado es requerido")
    private Long gradeLevelId;

    private Long periodId;
}
