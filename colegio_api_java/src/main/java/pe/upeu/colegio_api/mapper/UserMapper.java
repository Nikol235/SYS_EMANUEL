package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.UserRequestDTO;
import pe.edu.upeu.colegio_api.dto.UserResponseDTO;
import pe.edu.upeu.colegio_api.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deactivatedAt", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deactivatedAt", ignore = true)
    void updateEntity(UserRequestDTO dto, @MappingTarget User user);
}
