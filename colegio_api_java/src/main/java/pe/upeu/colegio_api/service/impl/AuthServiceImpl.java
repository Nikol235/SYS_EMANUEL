package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pe.edu.upeu.colegio_api.dto.AuthLoginRequestDTO;
import pe.edu.upeu.colegio_api.dto.AuthLoginResponseDTO;
import pe.edu.upeu.colegio_api.dto.UserResponseDTO;
import pe.edu.upeu.colegio_api.entity.User;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.UserMapper;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.security.JwtUtil;
import pe.edu.upeu.colegio_api.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public AuthLoginResponseDTO login(AuthLoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new AppException(HttpStatus.FORBIDDEN, "Usuario desactivado. Acérquese a Dirección.");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return new AuthLoginResponseDTO(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getFullName(),
                user.getPhotoUrl()
        );
    }

    @Override
    public UserResponseDTO me(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return userMapper.toResponseDTO(user);
    }
}
