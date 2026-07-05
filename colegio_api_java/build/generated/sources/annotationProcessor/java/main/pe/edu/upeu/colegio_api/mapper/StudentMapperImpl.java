package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;
import pe.edu.upeu.colegio_api.entity.GradeLevel;
import pe.edu.upeu.colegio_api.entity.Student;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:16-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentResponseDTO toResponseDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();

        studentResponseDTO.setGradeLevel( gradeLevelToGradeLevelInfo( student.getGradeLevel() ) );
        studentResponseDTO.setId( student.getId() );
        studentResponseDTO.setFirstName( student.getFirstName() );
        studentResponseDTO.setLastName( student.getLastName() );
        studentResponseDTO.setDni( student.getDni() );
        studentResponseDTO.setBirthDate( student.getBirthDate() );
        studentResponseDTO.setCodigo( student.getCodigo() );
        studentResponseDTO.setActive( student.getActive() );
        studentResponseDTO.setPhotoUrl( student.getPhotoUrl() );

        return studentResponseDTO;
    }

    protected StudentResponseDTO.GradeLevelInfo gradeLevelToGradeLevelInfo(GradeLevel gradeLevel) {
        if ( gradeLevel == null ) {
            return null;
        }

        StudentResponseDTO.GradeLevelInfo gradeLevelInfo = new StudentResponseDTO.GradeLevelInfo();

        gradeLevelInfo.setId( gradeLevel.getId() );
        gradeLevelInfo.setName( gradeLevel.getName() );
        gradeLevelInfo.setSection( gradeLevel.getSection() );

        return gradeLevelInfo;
    }
}
