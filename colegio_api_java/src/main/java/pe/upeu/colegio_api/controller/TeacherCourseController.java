package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.TeacherCourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.TeacherCourseResponseDTO;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.TeacherCourseService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher-courses")
@RequiredArgsConstructor
@Tag(name = "Teacher Courses")
public class TeacherCourseController {

    private final TeacherCourseService teacherCourseService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<TeacherCourseResponseDTO>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.ok(teacherCourseService.findAll(userDetails.getUsername(), role));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<TeacherCourseResponseDTO> create(@Valid @RequestBody TeacherCourseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherCourseService.create(dto));
    }

    @PatchMapping("/{id}/eval-names")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','DOCENTE')")
    public ResponseEntity<TeacherCourseResponseDTO> updateEvalNames(
            @PathVariable Long id,
            @RequestBody Map<String, List<String>> body,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.ok(teacherCourseService.updateEvalNames(id, body.get("evalNames"), userDetails.getUsername(), role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherCourseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
