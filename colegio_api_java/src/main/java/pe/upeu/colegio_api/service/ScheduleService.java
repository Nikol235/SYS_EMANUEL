package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.ScheduleRequestDTO;
import pe.edu.upeu.colegio_api.dto.ScheduleResponseDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponseDTO> findAll(Long gradeLevelId);
    ScheduleResponseDTO create(ScheduleRequestDTO dto);
    ScheduleResponseDTO update(Long id, ScheduleRequestDTO dto);
    void delete(Long id);
}
