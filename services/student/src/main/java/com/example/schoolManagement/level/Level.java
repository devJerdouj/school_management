package com.example.schoolManagement.level;

import com.example.schoolManagement.groupe.Group;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="levels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Level {
    @Id
    @Column(name = "level_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name = "name",nullable = false)
    private String name ;

    @Column(name = "total_cost")
    private double totalCost;

    @OneToMany(mappedBy = "level")
    private Set<Group> groups ;


}
