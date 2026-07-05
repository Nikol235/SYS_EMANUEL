package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.GradeResponseDTO;
import pe.edu.upeu.colegio_api.entity.Course;
import pe.edu.upeu.colegio_api.entity.Grade;
import pe.edu.upeu.colegio_api.entity.Student;
import pe.edu.upeu.colegio_api.entity.TeacherCourse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:16-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class GradeMapperImpl implements GradeMapper {

    @Override
    public GradeResponseDTO toResponseDTO(Grade grade) {
        if ( grade == null ) {
            return null;
        }

        GradeResponseDTO gradeResponseDTO = new GradeResponseDTO();

        gradeResponseDTO.setStudent( studentToStudentInfo( grade.getStudent() ) );
        gradeResponseDTO.setCourse( teacherCourseToCourseInfo( grade.getTeacherCourse() ) );
        gradeResponseDTO.setId( grade.getId() );
        gradeResponseDTO.setEvaluationName( grade.getEvaluationName() );
        gradeResponseDTO.setScore( grade.getScore() );

        return gradeResponseDTO;
    }

    protected GradeResponseDTO.StudentInfo studentToStudentInfo(Student student) {
        if ( student == null ) {
            return null;
        }

        GradeResponseDTO.StudentInfo studentInfo = new GradeResponseDTO.StudentInfo();

        studentInfo.setId( student.getId() );
        studentInfo.setFirstName( student.getFirstName() );
        studentInfo.setLastName( student.getLastName() );

        return studentInfo;
    }

    private Long teacherCourseCourseId(TeacherCourse teacherCourse) {
        Course course = teacherCourse.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getId();
    }

    private String teacherCourseCourseName(TeacherCourse teacherCourse) {
        Course course = teacherCourse.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getName();
    }

    private String teacherCourseCourseColor(TeacherCourse teacherCourse) {
        Course course = teacherCourse.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getColor();
    }

    protected GradeResponseDTO.CourseInfo teacherCourseToCourseInfo(TeacherCourse teacherCourse) {
        if ( teacherCourse == null ) {
            return null;
        }

        GradeResponseDTO.CourseInfo courseInfo = new GradeResponseDTO.CourseInfo();

        courseInfo.setId( teacherCourseCourseId( teacherCourse ) );
        courseInfo.setName( teacherCourseCourseName( teacherCourse ) );
        courseInfo.setColor( teacherCourseCourseColor( teacherCourse ) );

        return courseInfo;
    }
}
