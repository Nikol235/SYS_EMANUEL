package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.PaymentRequestDTO;
import pe.edu.upeu.colegio_api.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDTO> findAll(Long studentId, String username, String role);
    PaymentResponseDTO findById(Long id);
    PaymentResponseDTO create(PaymentRequestDTO dto);
    PaymentResponseDTO update(Long id, PaymentRequestDTO dto);
}
