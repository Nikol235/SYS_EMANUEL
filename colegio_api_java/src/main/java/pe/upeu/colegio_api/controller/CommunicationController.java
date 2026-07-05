package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.CommunicationRequestDTO;
import pe.edu.upeu.colegio_api.dto.CommunicationResponseDTO;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.CommunicationService;

@RestController
@RequestMapping("/api/communications")
@RequiredArgsConstructor
@Tag(name = "Communications")
public class CommunicationController {

    private final CommunicationService communicationService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<CommunicationResponseDTO>> findAll(
            @PageableDefault(size = 20) Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.ok(communicationService.findAll(userDetails.getUsername(), role, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','DOCENTE','AUXILIAR','SECRETARIA')")
    public ResponseEntity<CommunicationResponseDTO> create(
            @RequestBody CommunicationRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(communicationService.create(dto, userDetails.getUsername(), role));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','DOCENTE','AUXILIAR','SECRETARIA')")
    public ResponseEntity<CommunicationResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CommunicationRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        return ResponseEntity.ok(communicationService.update(id, dto, userDetails.getUsername(), role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','DOCENTE','AUXILIAR','SECRETARIA')")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String role = userRepository.findByUsername(userDetails.getUsername()).map(u -> u.getRole()).orElse("admin");
        communicationService.delete(id, userDetails.getUsername(), role);
        return ResponseEntity.noContent().build();
    }
}
