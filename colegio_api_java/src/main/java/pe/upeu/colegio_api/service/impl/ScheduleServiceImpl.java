package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.ScheduleRequestDTO;
import pe.edu.upeu.colegio_api.dto.ScheduleResponseDTO;
import pe.edu.upeu.colegio_api.entity.Schedule;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.ScheduleMapper;
import pe.edu.upeu.colegio_api.repository.GradeLevelRepository;
import pe.edu.upeu.colegio_api.repository.ScheduleRepository;
import pe.edu.upeu.colegio_api.repository.UserRepository;
import pe.edu.upeu.colegio_api.service.ScheduleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GradeLevelRepository gradeLevelRepository;
    private final UserRepository userRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> findAll(Long gradeLevelId) {
        List<Schedule> list = gradeLevelId != null
                ? scheduleRepository.findByGradeLevelIdOrderByStartTimeAscDayOfWeekAsc(gradeLevelId)
                : scheduleRepository.findAllByOrderByStartTimeAscDayOfWeekAsc();
        return list.stream().map(scheduleMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public ScheduleResponseDTO create(ScheduleRequestDTO dto) {
        Schedule s = new Schedule();
        s.setGradeLevel(gradeLevelRepository.findById(dto.getGradeLevelId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grado no encontrado")));
        s.setDayOfWeek(dto.getDayOfWeek());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        s.setSubject(dto.getSubject().trim());
        s.setColor(dto.getColor());
        if (dto.getTeacherId() != null) s.setTeacher(userRepository.findById(dto.getTeacherId()).orElse(null));
        return scheduleMapper.toResponseDTO(scheduleRepository.save(s));
    }

    @Override
    @Transactional
    public ScheduleResponseDTO update(Long id, ScheduleRequestDTO dto) {
        Schedule s = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Horario no encontrado: " + id));
        if (dto.getStartTime() != null) s.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) s.setEndTime(dto.getEndTime());
        if (dto.getSubject() != null) s.setSubject(dto.getSubject().trim());
        if (dto.getColor() != null) s.setColor(dto.getColor());
        if (dto.getTeacherId() != null) s.setTeacher(userRepository.findById(dto.getTeacherId()).orElse(null));
        return scheduleMapper.toResponseDTO(scheduleRepository.save(s));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Horario no encontrado: " + id));
        scheduleRepository.deleteById(id);
    }
}
