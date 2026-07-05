package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.GradeResponseDTO;
import pe.edu.upeu.colegio_api.entity.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(target = "student.id", source = "student.id")
    @Mapping(target = "student.firstName", source = "student.firstName")
    @Mapping(target = "student.lastName", source = "student.lastName")
    @Mapping(target = "course.id", source = "teacherCourse.course.id")
    @Mapping(target = "course.name", source = "teacherCourse.course.name")
    @Mapping(target = "course.color", source = "teacherCourse.course.color")
    GradeResponseDTO toResponseDTO(Grade grade);
}
