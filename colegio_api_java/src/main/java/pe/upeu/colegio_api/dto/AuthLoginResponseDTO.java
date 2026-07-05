package pe.edu.upeu.colegio_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginResponseDTO {
    private String token;
    private Long id;
    private String username;
    private String role;
    private String fullName;
    private String photoUrl;
}
