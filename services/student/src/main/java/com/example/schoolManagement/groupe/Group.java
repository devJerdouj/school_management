package com.example.schoolManagement.groupe;

import jakarta.persistence.*;
import com.example.schoolManagement.level.Level;
import lombok.*;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Group {

    @Id
    @Column(name="group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name="name", nullable = false)
    private String name ;

    @Column(name="students_number")
    private int studentsNumber;

    @Column(name="group_capacity")
    private int GroupCapacity;

    @ManyToOne
    @JoinColumn(name="level_id")
    private Level level ;

}
