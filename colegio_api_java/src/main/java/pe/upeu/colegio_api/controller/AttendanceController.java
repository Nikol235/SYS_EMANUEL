package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.AttendanceRequestDTO;
import pe.edu.upeu.colegio_api.dto.AttendanceResponseDTO;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> findAll(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String turno,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.ok(attendanceService.findAll(studentId, date, turno, userDetails.getUsername(), role));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE','AUXILIAR','DIRECTOR')")
    public ResponseEntity<AttendanceResponseDTO> save(@RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.save(dto));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE','AUXILIAR','DIRECTOR')")
    public ResponseEntity<List<AttendanceResponseDTO>> saveBulk(@RequestBody List<AttendanceRequestDTO> records) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.saveBulk(records));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE','AUXILIAR','DIRECTOR')")
    public ResponseEntity<Void> delete(
            @RequestParam Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String turno,
            @RequestParam(required = false) String tipo) {
        attendanceService.delete(studentId, date, turno, tipo);
        return ResponseEntity.noContent().build();
    }
}
