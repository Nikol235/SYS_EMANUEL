package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.AttendanceRequestDTO;
import pe.edu.upeu.colegio_api.dto.AttendanceResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    List<AttendanceResponseDTO> findAll(Long studentId, LocalDate date, String turno, String username, String role);
    AttendanceResponseDTO save(AttendanceRequestDTO dto);
    List<AttendanceResponseDTO> saveBulk(List<AttendanceRequestDTO> records);
    void delete(Long studentId, LocalDate date, String turno, String tipo);
}
