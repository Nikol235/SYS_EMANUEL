package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.AcademicPeriodRequestDTO;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;
import pe.edu.upeu.colegio_api.service.AcademicPeriodService;

import java.util.List;

@RestController
@RequestMapping("/api/academic-periods")
@RequiredArgsConstructor
@Tag(name = "Academic Periods")
public class AcademicPeriodController {

    private final AcademicPeriodService academicPeriodService;

    @GetMapping
    public ResponseEntity<List<AcademicPeriod>> findAll() {
        return ResponseEntity.ok(academicPeriodService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<AcademicPeriod> create(@Valid @RequestBody AcademicPeriodRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(academicPeriodService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<AcademicPeriod> update(@PathVariable Long id, @Valid @RequestBody AcademicPeriodRequestDTO dto) {
        return ResponseEntity.ok(academicPeriodService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        academicPeriodService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
