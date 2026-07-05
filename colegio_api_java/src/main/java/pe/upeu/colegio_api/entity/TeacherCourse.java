package pe.edu.upeu.colegio_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "teacher_courses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"teacher_id", "course_id", "grade_level_id"})
})
public class TeacherCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_level_id", nullable = false)
    private GradeLevel gradeLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private AcademicPeriod period;

    @Column(name = "eval_names", columnDefinition = "TEXT default '[\"N1\",\"N2\",\"N3\"]'")
    private String evalNames = "[\"N1\",\"N2\",\"N3\"]";
}
