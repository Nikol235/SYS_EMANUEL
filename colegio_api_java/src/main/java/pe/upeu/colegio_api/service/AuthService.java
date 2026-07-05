package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.AuthLoginRequestDTO;
import pe.edu.upeu.colegio_api.dto.AuthLoginResponseDTO;
import pe.edu.upeu.colegio_api.dto.UserResponseDTO;

public interface AuthService {
    AuthLoginResponseDTO login(AuthLoginRequestDTO dto);
    UserResponseDTO me(String username);
}
