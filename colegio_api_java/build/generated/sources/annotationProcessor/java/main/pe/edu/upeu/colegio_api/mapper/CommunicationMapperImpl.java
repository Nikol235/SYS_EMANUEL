package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.CommunicationResponseDTO;
import pe.edu.upeu.colegio_api.entity.Communication;
import pe.edu.upeu.colegio_api.entity.Course;
import pe.edu.upeu.colegio_api.entity.GradeLevel;
import pe.edu.upeu.colegio_api.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:16-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class CommunicationMapperImpl implements CommunicationMapper {

    @Override
    public CommunicationResponseDTO toResponseDTO(Communication communication) {
        if ( communication == null ) {
            return null;
        }

        CommunicationResponseDTO communicationResponseDTO = new CommunicationResponseDTO();

        communicationResponseDTO.setAuthor( userToAuthorInfo( communication.getAuthor() ) );
        communicationResponseDTO.setCourse( courseToCourseInfo( communication.getCourse() ) );
        communicationResponseDTO.setGradeLevel( gradeLevelToGradeLevelInfo( communication.getGradeLevel() ) );
        communicationResponseDTO.setId( communication.getId() );
        communicationResponseDTO.setTitle( communication.getTitle() );
        communicationResponseDTO.setBody( communication.getBody() );
        communicationResponseDTO.setType( communication.getType() );
        communicationResponseDTO.setAttachments( communication.getAttachments() );
        communicationResponseDTO.setStudentIds( communication.getStudentIds() );
        communicationResponseDTO.setCreatedAt( communication.getCreatedAt() );

        return communicationResponseDTO;
    }

    protected CommunicationResponseDTO.AuthorInfo userToAuthorInfo(User user) {
        if ( user == null ) {
            return null;
        }

        CommunicationResponseDTO.AuthorInfo authorInfo = new CommunicationResponseDTO.AuthorInfo();

        authorInfo.setId( user.getId() );
        authorInfo.setFullName( user.getFullName() );
        authorInfo.setRole( user.getRole() );

        return authorInfo;
    }

    protected CommunicationResponseDTO.CourseInfo courseToCourseInfo(Course course) {
        if ( course == null ) {
            return null;
        }

        CommunicationResponseDTO.CourseInfo courseInfo = new CommunicationResponseDTO.CourseInfo();

        courseInfo.setId( course.getId() );
        courseInfo.setName( course.getName() );
        courseInfo.setColor( course.getColor() );

        return courseInfo;
    }

    protected CommunicationResponseDTO.GradeLevelInfo gradeLevelToGradeLevelInfo(GradeLevel gradeLevel) {
        if ( gradeLevel == null ) {
            return null;
        }

        CommunicationResponseDTO.GradeLevelInfo gradeLevelInfo = new CommunicationResponseDTO.GradeLevelInfo();

        gradeLevelInfo.setId( gradeLevel.getId() );
        gradeLevelInfo.setName( gradeLevel.getName() );

        return gradeLevelInfo;
    }
}
