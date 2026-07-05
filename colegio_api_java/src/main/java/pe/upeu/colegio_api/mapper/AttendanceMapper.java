package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.AttendanceResponseDTO;
import pe.edu.upeu.colegio_api.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "student.id", source = "student.id")
    @Mapping(target = "student.firstName", source = "student.firstName")
    @Mapping(target = "student.lastName", source = "student.lastName")
    AttendanceResponseDTO toResponseDTO(Attendance attendance);
}
