package pe.edu.upeu.colegio_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 20)
    private String dni;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_level_id")
    private GradeLevel gradeLevel;

    @Column(length = 30)
    private String codigo;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;
}
