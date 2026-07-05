package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.CourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.CourseResponseDTO;
import pe.edu.upeu.colegio_api.entity.Course;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.CourseMapper;
import pe.edu.upeu.colegio_api.repository.CourseRepository;
import pe.edu.upeu.colegio_api.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAllByOrderByNameAsc().stream()
                .map(courseMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponseDTO findById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toResponseDTO)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Curso no encontrado: " + id));
    }

    @Override
    @Transactional
    public CourseResponseDTO create(CourseRequestDTO dto) {
        Course course = courseMapper.toEntity(dto);
        if (course.getColor() == null || course.getColor().isBlank()) course.setColor("#3B82F6");
        return courseMapper.toResponseDTO(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseResponseDTO update(Long id, CourseRequestDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Curso no encontrado: " + id));
        courseMapper.updateEntity(dto, course);
        return courseMapper.toResponseDTO(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Curso no encontrado: " + id));
        courseRepository.deleteById(id);
    }
}
