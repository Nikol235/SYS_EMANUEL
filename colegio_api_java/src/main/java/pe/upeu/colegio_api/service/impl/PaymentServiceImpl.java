package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.PaymentRequestDTO;
import pe.edu.upeu.colegio_api.dto.PaymentResponseDTO;
import pe.edu.upeu.colegio_api.entity.Payment;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.PaymentMapper;
import pe.edu.upeu.colegio_api.repository.PaymentRepository;
import pe.edu.upeu.colegio_api.repository.StudentRepository;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.PaymentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> findAll(Long studentId, String username, String role) {
        List<Payment> payments;
        if ("padre".equals(role)) {
            Long parentId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            List<Long> studentIds = studentRepository.findByParentId(parentId).stream().map(s -> s.getId()).toList();
            payments = studentIds.stream().flatMap(sid -> paymentRepository.findByStudentIdOrderByYearAscMonthAsc(sid).stream()).toList();
        } else if (studentId != null) {
            payments = paymentRepository.findByStudentIdOrderByYearAscMonthAsc(studentId);
        } else {
            payments = paymentRepository.findAll();
        }
        return payments.stream().map(paymentMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO findById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponseDTO)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Pago no encontrado: " + id));
    }

    @Override
    @Transactional
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        Payment p = new Payment();
        p.setStudent(studentRepository.findById(dto.getStudentId()).orElseThrow());
        p.setMonth(dto.getMonth());
        p.setYear(dto.getYear());
        p.setAmount(dto.getAmount());
        p.setPaid(dto.getPaid() != null ? dto.getPaid() : false);
        p.setPaidDate(dto.getPaidDate());
        return paymentMapper.toResponseDTO(paymentRepository.save(p));
    }

    @Override
    @Transactional
    public PaymentResponseDTO update(Long id, PaymentRequestDTO dto) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Pago no encontrado: " + id));
        if (dto.getPaid() != null) p.setPaid(dto.getPaid());
        if (dto.getPaidDate() != null) p.setPaidDate(dto.getPaidDate());
        if (dto.getAmount() != null) p.setAmount(dto.getAmount());
        return paymentMapper.toResponseDTO(paymentRepository.save(p));
    }
}
