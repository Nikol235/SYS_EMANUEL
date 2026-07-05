package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.DashboardAdminDTO;

import java.util.Map;

public interface DashboardService {
    DashboardAdminDTO getAdminDashboard();
    Map<String, Object> getDocenteDashboard(String username);
    Map<String, Object> getPadreDashboard(String username);
}
