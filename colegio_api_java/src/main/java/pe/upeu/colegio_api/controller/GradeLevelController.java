package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.GradeLevelRequestDTO;
import pe.edu.upeu.colegio_api.dto.GradeLevelResponseDTO;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;
import pe.edu.upeu.colegio_api.service.GradeLevelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grade-levels")
@RequiredArgsConstructor
@Tag(name = "Grade Levels")
public class GradeLevelController {

    private final GradeLevelService gradeLevelService;

    @GetMapping
    public ResponseEntity<List<GradeLevelResponseDTO>> findAll() {
        return ResponseEntity.ok(gradeLevelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeLevelResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gradeLevelService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<GradeLevelResponseDTO> create(@Valid @RequestBody GradeLevelRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeLevelService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<GradeLevelResponseDTO> update(@PathVariable Long id, @RequestBody GradeLevelRequestDTO dto) {
        return ResponseEntity.ok(gradeLevelService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradeLevelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<StudentResponseDTO>> findMembers(@PathVariable Long id) {
        return ResponseEntity.ok(gradeLevelService.findMembers(id));
    }

    @PostMapping("/{id}/members")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','AUXILIAR')")
    public ResponseEntity<Void> addMember(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        gradeLevelService.addMember(id, body.get("studentId"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/members/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','AUXILIAR')")
    public ResponseEntity<Void> removeMember(@PathVariable Long id, @PathVariable Long studentId) {
        gradeLevelService.removeMember(id, studentId);
        return ResponseEntity.noContent().build();
    }
}
