package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CommunicationRequestDTO {

    @NotBlank(message = "El título es requerido")
    private String title;

    private String body;
    private String type = "general";
    private Long courseId;
    private Long gradeLevelId;
    private List<String> attachments;
    private List<Long> studentIds;
}
