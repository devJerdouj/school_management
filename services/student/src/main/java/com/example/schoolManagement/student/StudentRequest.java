package com.example.schoolManagement.student;

import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.level.Level;
import com.example.schoolManagement.responsible.Responsible;
import jakarta.persistence.*;

import java.util.Date;

public record StudentRequest() {


    private Long id ;


    private String firstName ;


    private String lastName ;

    private String email ;


    private String code ;

    private String address;

    private String numberPhone;


    private Date birthDate ;


    private Level level ;


    private Group group ;


    private Responsible responsible;

}
