package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.UserRequestDTO;
import pe.edu.upeu.colegio_api.dto.UserResponseDTO;
import pe.edu.upeu.colegio_api.entity.User;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.UserMapper;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findStaff() {
        return userRepository.findByRoleInAndActiveTrue(
                List.of("docente", "auxiliar", "director", "secretaria")
        ).stream().map(userMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + id));
    }

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        String rawPw = (dto.getPassword() != null && !dto.getPassword().isBlank()) ? dto.getPassword() : "123456";
        user.setPasswordHash(passwordEncoder.encode(rawPw));
        user.setRole(dto.getRole());
        user.setFullName(dto.getFullName());
        user.setDni(dto.getDni());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setActive(dto.getActive() != null ? dto.getActive() : true);
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + id));

        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isBlank())
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRole() != null) user.setRole(dto.getRole());
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getDni() != null) user.setDni(dto.getDni());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getPhotoUrl() != null) user.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getActive() != null) {
            user.setActive(dto.getActive());
            user.setDeactivatedAt(dto.getActive() ? null : LocalDateTime.now());
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + id));
        userRepository.deleteById(id);
    }
}
