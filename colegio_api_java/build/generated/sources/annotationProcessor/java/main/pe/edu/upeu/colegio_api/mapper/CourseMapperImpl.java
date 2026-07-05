package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.CourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.CourseResponseDTO;
import pe.edu.upeu.colegio_api.entity.Course;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:16-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseResponseDTO toResponseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();

        courseResponseDTO.setId( course.getId() );
        courseResponseDTO.setName( course.getName() );
        courseResponseDTO.setColor( course.getColor() );
        courseResponseDTO.setDescription( course.getDescription() );

        return courseResponseDTO;
    }

    @Override
    public Course toEntity(CourseRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Course course = new Course();

        course.setName( dto.getName() );
        course.setColor( dto.getColor() );
        course.setDescription( dto.getDescription() );

        return course;
    }

    @Override
    public void updateEntity(CourseRequestDTO dto, Course course) {
        if ( dto == null ) {
            return;
        }

        course.setName( dto.getName() );
        course.setColor( dto.getColor() );
        course.setDescription( dto.getDescription() );
    }
}
