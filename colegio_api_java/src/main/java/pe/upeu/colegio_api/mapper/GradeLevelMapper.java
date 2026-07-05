package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.GradeLevelResponseDTO;
import pe.edu.upeu.colegio_api.entity.GradeLevel;

@Mapper(componentModel = "spring")
public interface GradeLevelMapper {

    @Mapping(target = "period.id", source = "period.id")
    @Mapping(target = "period.name", source = "period.name")
    @Mapping(target = "tutor.id", source = "tutor.id")
    @Mapping(target = "tutor.fullName", source = "tutor.fullName")
    @Mapping(target = "tutor.photoUrl", source = "tutor.photoUrl")
    @Mapping(target = "memberCount", ignore = true)
    GradeLevelResponseDTO toResponseDTO(GradeLevel gradeLevel);
}
