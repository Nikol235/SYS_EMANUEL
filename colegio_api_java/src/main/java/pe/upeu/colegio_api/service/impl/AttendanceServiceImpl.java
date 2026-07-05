package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.AttendanceRequestDTO;
import pe.edu.upeu.colegio_api.dto.AttendanceResponseDTO;
import pe.edu.upeu.colegio_api.entity.Attendance;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.AttendanceMapper;
import pe.edu.upeu.colegio_api.repository.AttendanceRepository;
import pe.edu.upeu.colegio_api.repository.StudentRepository;
import pe.edu.upeu.colegio_api.service.AttendanceService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> findAll(Long studentId, LocalDate date, String turno, String username, String role) {
        List<Attendance> list;
        if (studentId != null) {
            list = attendanceRepository.findByStudentIdOrderByDateDesc(studentId);
            if (date != null) list = list.stream().filter(a -> a.getDate().equals(date)).toList();
        } else {
            list = attendanceRepository.findAll();
        }
        return list.stream().map(attendanceMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public AttendanceResponseDTO save(AttendanceRequestDTO dto) {
        String turno = dto.getTurno() != null ? dto.getTurno() : "mañana";
        String tipo = dto.getTipo() != null ? dto.getTipo() : "entrada";

        Attendance att = attendanceRepository.findByStudentIdAndDateAndTurnoAndTipo(
                dto.getStudentId(), dto.getDate(), turno, tipo
        ).orElseGet(() -> {
            Attendance a = new Attendance();
            a.setStudent(studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Alumno no encontrado")));
            a.setDate(dto.getDate());
            a.setTurno(turno);
            a.setTipo(tipo);
            return a;
        });

        att.setStatus(dto.getStatus());
        att.setUpdatedAt(LocalDateTime.now());
        return attendanceMapper.toResponseDTO(attendanceRepository.save(att));
    }

    @Override
    @Transactional
    public List<AttendanceResponseDTO> saveBulk(List<AttendanceRequestDTO> records) {
        return records.stream().map(this::save).toList();
    }

    @Override
    @Transactional
    public void delete(Long studentId, LocalDate date, String turno, String tipo) {
        if (studentId == null || date == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "student_id y date son requeridos");
        }
        if (turno != null && tipo != null) {
            attendanceRepository.findByStudentIdAndDateAndTurnoAndTipo(studentId, date, turno, tipo)
                    .ifPresent(attendanceRepository::delete);
        } else {
            attendanceRepository.deleteByStudentIdAndDate(studentId, date);
        }
    }
}
