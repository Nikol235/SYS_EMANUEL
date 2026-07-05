package pe.edu.upeu.colegio_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "grades", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "teacher_course_id", "evaluation_name"})
})
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_course_id", nullable = false)
    private TeacherCourse teacherCourse;

    @Column(name = "evaluation_name", nullable = false, length = 50)
    private String evaluationName;

    @Column(nullable = false)
    private Double score;
}
