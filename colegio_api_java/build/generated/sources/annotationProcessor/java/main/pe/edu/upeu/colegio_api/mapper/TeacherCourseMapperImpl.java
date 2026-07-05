package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.TeacherCourseResponseDTO;
import pe.edu.upeu.colegio_api.entity.Course;
import pe.edu.upeu.colegio_api.entity.GradeLevel;
import pe.edu.upeu.colegio_api.entity.TeacherCourse;
import pe.edu.upeu.colegio_api.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:15-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class TeacherCourseMapperImpl implements TeacherCourseMapper {

    @Override
    public TeacherCourseResponseDTO toResponseDTO(TeacherCourse teacherCourse) {
        if ( teacherCourse == null ) {
            return null;
        }

        TeacherCourseResponseDTO teacherCourseResponseDTO = new TeacherCourseResponseDTO();

        teacherCourseResponseDTO.setTeacher( userToTeacherInfo( teacherCourse.getTeacher() ) );
        teacherCourseResponseDTO.setCourse( courseToCourseInfo( teacherCourse.getCourse() ) );
        teacherCourseResponseDTO.setGradeLevel( gradeLevelToGradeLevelInfo( teacherCourse.getGradeLevel() ) );
        teacherCourseResponseDTO.setId( teacherCourse.getId() );
        teacherCourseResponseDTO.setEvalNames( teacherCourse.getEvalNames() );

        return teacherCourseResponseDTO;
    }

    protected TeacherCourseResponseDTO.TeacherInfo userToTeacherInfo(User user) {
        if ( user == null ) {
            return null;
        }

        TeacherCourseResponseDTO.TeacherInfo teacherInfo = new TeacherCourseResponseDTO.TeacherInfo();

        teacherInfo.setId( user.getId() );
        teacherInfo.setFullName( user.getFullName() );

        return teacherInfo;
    }

    protected TeacherCourseResponseDTO.CourseInfo courseToCourseInfo(Course course) {
        if ( course == null ) {
            return null;
        }

        TeacherCourseResponseDTO.CourseInfo courseInfo = new TeacherCourseResponseDTO.CourseInfo();

        courseInfo.setId( course.getId() );
        courseInfo.setName( course.getName() );
        courseInfo.setColor( course.getColor() );

        return courseInfo;
    }

    protected TeacherCourseResponseDTO.GradeLevelInfo gradeLevelToGradeLevelInfo(GradeLevel gradeLevel) {
        if ( gradeLevel == null ) {
            return null;
        }

        TeacherCourseResponseDTO.GradeLevelInfo gradeLevelInfo = new TeacherCourseResponseDTO.GradeLevelInfo();

        gradeLevelInfo.setId( gradeLevel.getId() );
        gradeLevelInfo.setName( gradeLevel.getName() );
        gradeLevelInfo.setSection( gradeLevel.getSection() );

        return gradeLevelInfo;
    }
}
