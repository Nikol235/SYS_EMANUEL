package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    private String color;
    private String description;
}
