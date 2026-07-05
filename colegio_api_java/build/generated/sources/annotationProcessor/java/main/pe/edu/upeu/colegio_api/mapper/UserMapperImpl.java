package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.UserRequestDTO;
import pe.edu.upeu.colegio_api.dto.UserResponseDTO;
import pe.edu.upeu.colegio_api.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:16-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDTO toResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( user.getId() );
        userResponseDTO.setUsername( user.getUsername() );
        userResponseDTO.setRole( user.getRole() );
        userResponseDTO.setFullName( user.getFullName() );
        userResponseDTO.setDni( user.getDni() );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setPhone( user.getPhone() );
        userResponseDTO.setPhotoUrl( user.getPhotoUrl() );
        userResponseDTO.setActive( user.getActive() );
        userResponseDTO.setCreatedAt( user.getCreatedAt() );

        return userResponseDTO;
    }

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setRole( dto.getRole() );
        user.setFullName( dto.getFullName() );
        user.setDni( dto.getDni() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setPhotoUrl( dto.getPhotoUrl() );
        user.setActive( dto.getActive() );

        return user;
    }

    @Override
    public void updateEntity(UserRequestDTO dto, User user) {
        if ( dto == null ) {
            return;
        }

        user.setUsername( dto.getUsername() );
        user.setRole( dto.getRole() );
        user.setFullName( dto.getFullName() );
        user.setDni( dto.getDni() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setPhotoUrl( dto.getPhotoUrl() );
        user.setActive( dto.getActive() );
    }
}
