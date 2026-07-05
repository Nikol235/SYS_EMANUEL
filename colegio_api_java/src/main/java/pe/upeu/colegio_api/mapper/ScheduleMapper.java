package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.ScheduleResponseDTO;
import pe.edu.upeu.colegio_api.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(target = "gradeLevel.id", source = "gradeLevel.id")
    @Mapping(target = "gradeLevel.name", source = "gradeLevel.name")
    @Mapping(target = "gradeLevel.section", source = "gradeLevel.section")
    @Mapping(target = "teacher.id", source = "teacher.id")
    @Mapping(target = "teacher.fullName", source = "teacher.fullName")
    ScheduleResponseDTO toResponseDTO(Schedule schedule);
}
