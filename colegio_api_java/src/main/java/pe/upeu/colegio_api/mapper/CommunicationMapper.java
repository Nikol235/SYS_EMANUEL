package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.CommunicationResponseDTO;
import pe.edu.upeu.colegio_api.entity.Communication;

@Mapper(componentModel = "spring")
public interface CommunicationMapper {

    @Mapping(target = "author.id", source = "author.id")
    @Mapping(target = "author.fullName", source = "author.fullName")
    @Mapping(target = "author.role", source = "author.role")
    @Mapping(target = "course.id", source = "course.id")
    @Mapping(target = "course.name", source = "course.name")
    @Mapping(target = "course.color", source = "course.color")
    @Mapping(target = "gradeLevel.id", source = "gradeLevel.id")
    @Mapping(target = "gradeLevel.name", source = "gradeLevel.name")
    CommunicationResponseDTO toResponseDTO(Communication communication);
}
