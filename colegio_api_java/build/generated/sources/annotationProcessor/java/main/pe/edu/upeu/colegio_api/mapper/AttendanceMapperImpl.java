package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.AttendanceResponseDTO;
import pe.edu.upeu.colegio_api.entity.Attendance;
import pe.edu.upeu.colegio_api.entity.Student;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:15-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public AttendanceResponseDTO toResponseDTO(Attendance attendance) {
        if ( attendance == null ) {
            return null;
        }

        AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO();

        attendanceResponseDTO.setStudent( studentToStudentInfo( attendance.getStudent() ) );
        attendanceResponseDTO.setId( attendance.getId() );
        attendanceResponseDTO.setDate( attendance.getDate() );
        attendanceResponseDTO.setStatus( attendance.getStatus() );
        attendanceResponseDTO.setTurno( attendance.getTurno() );
        attendanceResponseDTO.setTipo( attendance.getTipo() );
        attendanceResponseDTO.setUpdatedAt( attendance.getUpdatedAt() );

        return attendanceResponseDTO;
    }

    protected AttendanceResponseDTO.StudentInfo studentToStudentInfo(Student student) {
        if ( student == null ) {
            return null;
        }

        AttendanceResponseDTO.StudentInfo studentInfo = new AttendanceResponseDTO.StudentInfo();

        studentInfo.setId( student.getId() );
        studentInfo.setFirstName( student.getFirstName() );
        studentInfo.setLastName( student.getLastName() );

        return studentInfo;
    }
}
