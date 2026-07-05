package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.StudentRequestDTO;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;
import pe.edu.upeu.colegio_api.entity.*;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.StudentMapper;
import pe.edu.upeu.colegio_api.repository.*;
import pe.edu.upeu.colegio_api.service.StudentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final List<String> SCHOOL_MONTHS = List.of(
            "Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    );

    private final StudentRepository studentRepository;
    private final GradeLevelRepository gradeLevelRepository;
    private final UserRepository userRepository;
    private final ParentStudentRepository parentStudentRepository;
    private final PaymentRepository paymentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findAll(String username, String role) {
        List<Student> students;
        if ("padre".equals(role)) {
            User parent = userRepository.findByUsername(username).orElseThrow();
            students = studentRepository.findByParentId(parent.getId());
        } else if ("docente".equals(role)) {
            User teacher = userRepository.findByUsername(username).orElseThrow();
            students = studentRepository.findByTeacherId(teacher.getId());
        } else {
            students = studentRepository.findByActiveTrueOrderByLastNameAscFirstNameAsc();
        }
        return students.stream().map(s -> enrichStudent(studentMapper.toResponseDTO(s), s)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> findAllPaged(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(s -> enrichStudent(studentMapper.toResponseDTO(s), s));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO findById(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Alumno no encontrado: " + id));
        return enrichStudent(studentMapper.toResponseDTO(s), s);
    }

    private StudentResponseDTO enrichStudent(StudentResponseDTO dto, Student student) {
        parentStudentRepository.findByStudentId(student.getId()).stream().findFirst()
                .ifPresent(ps -> {
                    dto.setParentId(ps.getParent().getId());
                    dto.setParentUsername(ps.getParent().getUsername());
                    dto.setParentPhone(ps.getParent().getPhone());
                });
        return dto;
    }

    @Override
    @Transactional
    public StudentResponseDTO create(StudentRequestDTO dto, String creatorRole, String creatorName) {
        GradeLevel gradeLevel = gradeLevelRepository.findById(dto.getGradeLevelId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grado no encontrado"));

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDni(dto.getDni());
        student.setBirthDate(dto.getBirthDate());
        student.setGradeLevel(gradeLevel);
        student.setPhotoUrl(dto.getPhotoUrl());
        student.setActive(true);
        student = studentRepository.save(student);

        int year = LocalDate.now().getYear();
        student.setCodigo(String.format("EMN-%d-%04d", year, student.getId()));
        student = studentRepository.save(student);

        String firstLastName = normalize(dto.getLastName().trim().split("\\s+")[0]);
        String firstFirstName = normalize(dto.getFirstName().trim().split("\\s+")[0]);
        String baseUsername = firstFirstName + "." + firstLastName;
        String username = baseUsername;
        long existingCount = userRepository.findAll().stream()
                .filter(u -> u.getUsername().startsWith(baseUsername) && Boolean.TRUE.equals(u.getActive()))
                .count();
        if (existingCount > 0) username = baseUsername + (existingCount + 1);

        String rawPassword = dto.getDni() != null ? dto.getDni() : java.util.UUID.randomUUID().toString().substring(0, 8);

        User parent = new User();
        parent.setUsername(username);
        parent.setPasswordHash(passwordEncoder.encode(rawPassword));
        parent.setRole("padre");
        parent.setFullName(dto.getFirstName() + " " + dto.getLastName());
        parent.setDni(dto.getDni());
        parent.setPhone(dto.getParentPhone());
        parent.setActive(true);
        parent = userRepository.save(parent);

        ParentStudent ps = new ParentStudent();
        ps.setParent(parent);
        ps.setStudent(student);
        parentStudentRepository.save(ps);

        generatePayments(student, year, dto.getMonthlyFee() != null ? dto.getMonthlyFee() : new BigDecimal("350.00"));

        return enrichStudent(studentMapper.toResponseDTO(student), student);
    }

    private void generatePayments(Student student, int year, BigDecimal amount) {
        for (String month : SCHOOL_MONTHS) {
            Payment p = new Payment();
            p.setStudent(student);
            p.setMonth(month);
            p.setYear(year);
            p.setAmount(amount);
            p.setPaid(false);
            try {
                paymentRepository.save(p);
            } catch (Exception e) {
                // ignore duplicate
            }
        }
    }

    private String normalize(String s) {
        return s.toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[ñ]", "n");
    }

    @Override
    @Transactional
    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Alumno no encontrado: " + id));

        if (dto.getFirstName() != null) student.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) student.setLastName(dto.getLastName());
        if (dto.getDni() != null) student.setDni(dto.getDni());
        if (dto.getBirthDate() != null) student.setBirthDate(dto.getBirthDate());
        if (dto.getGradeLevelId() != null) {
            student.setGradeLevel(gradeLevelRepository.findById(dto.getGradeLevelId()).orElseThrow());
        }
        if (dto.getPhotoUrl() != null) student.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getActive() != null) {
            student.setActive(dto.getActive());
            student.setDeactivatedAt(dto.getActive() ? null : LocalDateTime.now());
            boolean active = dto.getActive();
            parentStudentRepository.findByStudentId(id).forEach(ps -> {
                ps.getParent().setActive(active);
                ps.getParent().setDeactivatedAt(active ? null : LocalDateTime.now());
                userRepository.save(ps.getParent());
            });
        }
        if (dto.getMonthlyFee() != null) {
            paymentRepository.findByStudentIdAndPaidFalse(id).forEach(p -> {
                p.setAmount(dto.getMonthlyFee());
                paymentRepository.save(p);
            });
        }
        if (dto.getParentPhone() != null) {
            parentStudentRepository.findByStudentId(id).forEach(ps -> {
                ps.getParent().setPhone(dto.getParentPhone());
                userRepository.save(ps.getParent());
            });
        }

        return enrichStudent(studentMapper.toResponseDTO(studentRepository.save(student)), student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Alumno no encontrado: " + id));

        List<Long> parentIds = parentStudentRepository.findParentIdsByStudentId(id);
        parentStudentRepository.deleteByStudentId(id);
        paymentRepository.deleteByStudentId(id);
        studentRepository.delete(student);

        for (Long parentId : parentIds) {
            if (!parentStudentRepository.existsByParentId(parentId)) {
                userRepository.deleteById(parentId);
            }
        }
    }

    @Override
    @Transactional
    public void generatePayments() {
        int year = LocalDate.now().getYear();
        List<Student> students = studentRepository.findAllActive();
        for (Student s : students) {
            generatePayments(s, year, new BigDecimal("350.00"));
        }
    }
}
