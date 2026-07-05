package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;
import pe.edu.upeu.colegio_api.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "gradeLevel.id", source = "gradeLevel.id")
    @Mapping(target = "gradeLevel.name", source = "gradeLevel.name")
    @Mapping(target = "gradeLevel.section", source = "gradeLevel.section")
    @Mapping(target = "parentPhone", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "parentUsername", ignore = true)
    StudentResponseDTO toResponseDTO(Student student);
}
