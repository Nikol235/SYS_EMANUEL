package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.TeacherCourseResponseDTO;
import pe.edu.upeu.colegio_api.entity.TeacherCourse;

@Mapper(componentModel = "spring")
public interface TeacherCourseMapper {

    @Mapping(target = "teacher.id", source = "teacher.id")
    @Mapping(target = "teacher.fullName", source = "teacher.fullName")
    @Mapping(target = "course.id", source = "course.id")
    @Mapping(target = "course.name", source = "course.name")
    @Mapping(target = "course.color", source = "course.color")
    @Mapping(target = "gradeLevel.id", source = "gradeLevel.id")
    @Mapping(target = "gradeLevel.name", source = "gradeLevel.name")
    @Mapping(target = "gradeLevel.section", source = "gradeLevel.section")
    TeacherCourseResponseDTO toResponseDTO(TeacherCourse teacherCourse);
}
