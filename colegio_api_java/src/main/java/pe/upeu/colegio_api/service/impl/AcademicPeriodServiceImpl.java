package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.AcademicPeriodRequestDTO;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.repository.AcademicPeriodRepository;
import pe.edu.upeu.colegio_api.service.AcademicPeriodService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicPeriodServiceImpl implements AcademicPeriodService {

    private final AcademicPeriodRepository academicPeriodRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AcademicPeriod> findAll() {
        return academicPeriodRepository.findAll();
    }

    @Override
    @Transactional
    public AcademicPeriod create(AcademicPeriodRequestDTO dto) {
        AcademicPeriod period = new AcademicPeriod();
        period.setName(dto.getName());
        return academicPeriodRepository.save(period);
    }

    @Override
    @Transactional
    public AcademicPeriod update(Long id, AcademicPeriodRequestDTO dto) {
        AcademicPeriod period = academicPeriodRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Período no encontrado: " + id));
        period.setName(dto.getName());
        return academicPeriodRepository.save(period);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        academicPeriodRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Período no encontrado: " + id));
        academicPeriodRepository.deleteById(id);
    }
}
