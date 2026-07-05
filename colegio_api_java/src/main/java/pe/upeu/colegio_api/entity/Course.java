package pe.edu.upeu.colegio_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 20, columnDefinition = "varchar(20) default '#3B82F6'")
    private String color;

    @Column(length = 500)
    private String description;
}
