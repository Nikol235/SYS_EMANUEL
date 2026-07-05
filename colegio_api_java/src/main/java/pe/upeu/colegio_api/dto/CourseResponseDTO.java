package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

@Data
public class CourseResponseDTO {
    private Long id;
    private String name;
    private String color;
    private String description;
}
