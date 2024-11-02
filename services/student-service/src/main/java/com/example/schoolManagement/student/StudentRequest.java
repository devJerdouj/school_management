package com.example.schoolManagement.student;

import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.level.Level;
import com.example.schoolManagement.responsible.Responsible;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record StudentRequest(



        Long id ,

        @NotBlank(message = "Student first name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName ,


        @NotBlank(message = "Student last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName ,


        @NotBlank(message = "Student email is required")
        @Email(message = "Please provide a valid email address")
        String email ,

        @NotBlank(message = "Student code is required")
        @Pattern(regexp = "^[A-Za-z0-9]{6,10}$", message = "Code must be alphanumeric and between 6 and 10 characters")
        String code ,

        @NotBlank(message = "Student address is required")
        @Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters")
        String address,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be a valid format and between 10 and 15 digits")
        String numberPhone,

        @NotNull(message = "Student birthDate is required")
        @Past(message = "BirthDate must be in the past")
        LocalDate birthDate ,

        @NotNull(message = "Student level ID is required")
        Long levelId, // Réception de l'ID du Level

        Long groupId, // Réception de l'ID du Group

        String responsibleFirstname,


        Long responsibleId , // Réception de l'ID du Responsible


        Long paymentPlanId
) {




}
