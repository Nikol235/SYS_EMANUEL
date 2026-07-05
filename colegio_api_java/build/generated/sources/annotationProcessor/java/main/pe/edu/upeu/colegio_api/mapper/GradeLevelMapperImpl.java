package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.GradeLevelResponseDTO;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;
import pe.edu.upeu.colegio_api.entity.GradeLevel;
import pe.edu.upeu.colegio_api.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:16-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class GradeLevelMapperImpl implements GradeLevelMapper {

    @Override
    public GradeLevelResponseDTO toResponseDTO(GradeLevel gradeLevel) {
        if ( gradeLevel == null ) {
            return null;
        }

        GradeLevelResponseDTO gradeLevelResponseDTO = new GradeLevelResponseDTO();

        gradeLevelResponseDTO.setPeriod( academicPeriodToPeriodInfo( gradeLevel.getPeriod() ) );
        gradeLevelResponseDTO.setTutor( userToTutorInfo( gradeLevel.getTutor() ) );
        gradeLevelResponseDTO.setId( gradeLevel.getId() );
        gradeLevelResponseDTO.setName( gradeLevel.getName() );
        gradeLevelResponseDTO.setSection( gradeLevel.getSection() );
        gradeLevelResponseDTO.setColor( gradeLevel.getColor() );
        gradeLevelResponseDTO.setPhotoUrl( gradeLevel.getPhotoUrl() );

        return gradeLevelResponseDTO;
    }

    protected GradeLevelResponseDTO.PeriodInfo academicPeriodToPeriodInfo(AcademicPeriod academicPeriod) {
        if ( academicPeriod == null ) {
            return null;
        }

        GradeLevelResponseDTO.PeriodInfo periodInfo = new GradeLevelResponseDTO.PeriodInfo();

        periodInfo.setId( academicPeriod.getId() );
        periodInfo.setName( academicPeriod.getName() );

        return periodInfo;
    }

    protected GradeLevelResponseDTO.TutorInfo userToTutorInfo(User user) {
        if ( user == null ) {
            return null;
        }

        GradeLevelResponseDTO.TutorInfo tutorInfo = new GradeLevelResponseDTO.TutorInfo();

        tutorInfo.setId( user.getId() );
        tutorInfo.setFullName( user.getFullName() );
        tutorInfo.setPhotoUrl( user.getPhotoUrl() );

        return tutorInfo;
    }
}
