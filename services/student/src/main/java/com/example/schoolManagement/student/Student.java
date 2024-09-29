package com.example.schoolManagement.student;

import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.responsible.Responsible;
import jakarta.persistence.*;
import com.example.schoolManagement.level.Level;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name="first_name",nullable = false)
    private String firstName ;

    @Column(name="last_name",nullable = false)
    private String lastName ;

    private String email ;

    @Column(unique = true)
    private String code ;

    private String address;

    @Column(name="number_phone")
    private String numberPhone;

    @Column(name="birth_date")
    private Date birthDate ;

    @ManyToOne
    @JoinColumn(name="level_id")
    private Level level ;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",nullable = false,updatable = false)
    private Date createAt ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updateAt ;

    @Column(name = "payment_plan_id")
    private Long paymentPlanId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="responsible_id")
    private Responsible responsible;

}
