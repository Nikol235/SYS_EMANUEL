package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.AcademicPeriodRequestDTO;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;

import java.util.List;

public interface AcademicPeriodService {
    List<AcademicPeriod> findAll();
    AcademicPeriod create(AcademicPeriodRequestDTO dto);
    AcademicPeriod update(Long id, AcademicPeriodRequestDTO dto);
    void delete(Long id);
}
