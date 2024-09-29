package com.example.schoolManagement.responsible;
import com.example.schoolManagement.student.Student;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "responsibles")
public class Responsible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="responsible_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.ALL)
    private Set<Student> students;


}
