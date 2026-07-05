package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.ScheduleResponseDTO;
import pe.edu.upeu.colegio_api.entity.GradeLevel;
import pe.edu.upeu.colegio_api.entity.Schedule;
import pe.edu.upeu.colegio_api.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:15-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public ScheduleResponseDTO toResponseDTO(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        ScheduleResponseDTO scheduleResponseDTO = new ScheduleResponseDTO();

        scheduleResponseDTO.setGradeLevel( gradeLevelToGradeLevelInfo( schedule.getGradeLevel() ) );
        scheduleResponseDTO.setTeacher( userToTeacherInfo( schedule.getTeacher() ) );
        scheduleResponseDTO.setId( schedule.getId() );
        scheduleResponseDTO.setDayOfWeek( schedule.getDayOfWeek() );
        scheduleResponseDTO.setStartTime( schedule.getStartTime() );
        scheduleResponseDTO.setEndTime( schedule.getEndTime() );
        scheduleResponseDTO.setSubject( schedule.getSubject() );
        scheduleResponseDTO.setColor( schedule.getColor() );

        return scheduleResponseDTO;
    }

    protected ScheduleResponseDTO.GradeLevelInfo gradeLevelToGradeLevelInfo(GradeLevel gradeLevel) {
        if ( gradeLevel == null ) {
            return null;
        }

        ScheduleResponseDTO.GradeLevelInfo gradeLevelInfo = new ScheduleResponseDTO.GradeLevelInfo();

        gradeLevelInfo.setId( gradeLevel.getId() );
        gradeLevelInfo.setName( gradeLevel.getName() );
        gradeLevelInfo.setSection( gradeLevel.getSection() );

        return gradeLevelInfo;
    }

    protected ScheduleResponseDTO.TeacherInfo userToTeacherInfo(User user) {
        if ( user == null ) {
            return null;
        }

        ScheduleResponseDTO.TeacherInfo teacherInfo = new ScheduleResponseDTO.TeacherInfo();

        teacherInfo.setId( user.getId() );
        teacherInfo.setFullName( user.getFullName() );

        return teacherInfo;
    }
}
