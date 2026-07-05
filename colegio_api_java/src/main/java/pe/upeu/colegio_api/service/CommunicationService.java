package pe.edu.upeu.colegio_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.colegio_api.dto.CommunicationRequestDTO;
import pe.edu.upeu.colegio_api.dto.CommunicationResponseDTO;

public interface CommunicationService {
    Page<CommunicationResponseDTO> findAll(String username, String role, Pageable pageable);
    CommunicationResponseDTO create(CommunicationRequestDTO dto, String username, String role);
    CommunicationResponseDTO update(Long id, CommunicationRequestDTO dto, String username, String role);
    void delete(Long id, String username, String role);
}
