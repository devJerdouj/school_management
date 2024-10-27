package com.example.dto;

import java.time.LocalDate;

public record StudentDto (
    Long id,

    String firstName,

    String lastName,

    String email,

    String responsibleFirstname,

    String responsibleEmail,

    String code,

    LocalDate birthDate,

    Long paymentPlanID
){}
