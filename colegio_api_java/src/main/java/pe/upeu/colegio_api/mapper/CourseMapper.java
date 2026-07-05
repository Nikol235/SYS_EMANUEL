package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.CourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.CourseResponseDTO;
import pe.edu.upeu.colegio_api.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseResponseDTO toResponseDTO(Course course);

    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(CourseRequestDTO dto, @MappingTarget Course course);
}
