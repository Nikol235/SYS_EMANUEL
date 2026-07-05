package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleRequestDTO {

    @NotNull(message = "El grado es requerido")
    private Long gradeLevelId;

    @NotNull(message = "El día es requerido")
    private Integer dayOfWeek;

    @NotBlank(message = "La hora de inicio es requerida")
    private String startTime;

    @NotBlank(message = "La hora de fin es requerida")
    private String endTime;

    @NotBlank(message = "El curso/materia es requerido")
    private String subject;

    private Long teacherId;
    private String color;
}
