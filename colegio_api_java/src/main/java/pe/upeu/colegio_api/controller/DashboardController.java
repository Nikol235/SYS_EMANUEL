package pe.edu.upeu.colegio_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.colegio_api.dto.DashboardAdminDTO;
import pe.edu.upeu.colegio_api.service.DashboardService;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR','SECRETARIA','AUXILIAR')")
    public ResponseEntity<DashboardAdminDTO> adminDashboard() {
        return ResponseEntity.ok(dashboardService.getAdminDashboard());
    }

    @GetMapping("/docente")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Map<String, Object>> docenteDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(dashboardService.getDocenteDashboard(userDetails.getUsername()));
    }

    @GetMapping("/padre")
    @PreAuthorize("hasRole('PADRE')")
    public ResponseEntity<Map<String, Object>> padreDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(dashboardService.getPadreDashboard(userDetails.getUsername()));
    }
}
