package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.GradeRequestDTO;
import pe.edu.upeu.colegio_api.dto.GradeResponseDTO;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.GradeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
@Tag(name = "Grades")
public class GradeController {

    private final GradeService gradeService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<GradeResponseDTO>> findAll(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherCourseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.ok(gradeService.findAll(studentId, teacherCourseId, userDetails.getUsername(), role));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    public ResponseEntity<GradeResponseDTO> saveOrUpdate(
            @RequestBody GradeRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.saveOrUpdate(dto, userDetails.getUsername(), role));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    public ResponseEntity<GradeResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        return ResponseEntity.ok(gradeService.update(id, body.get("score")));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCENTE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
