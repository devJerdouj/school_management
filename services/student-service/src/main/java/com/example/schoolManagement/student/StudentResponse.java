package com.example.schoolManagement.student;

import com.example.schoolManagement.groupe.GroupResponse;
import com.example.schoolManagement.level.LevelResponse;
import com.example.schoolManagement.responsible.ResponsibleResponse;

import java.time.LocalDate;

public record StudentResponse(

        Long id,

        String firstName,

        String lastName,

        String email,

        String responsibleFirstname,

        String responsibleEmail,

        String code,

        LocalDate birthDate,

        Long paymentPlanID,
        LevelResponse level

        /*Long id,

        String firstName,

        String lastName,

        String email,

        String code,

        String address,

        String numberPhone,

        LocalDate birthDate,

        Long paymentPlanID,

        LevelResponse level,  // Inclus les détails de Level

        GroupResponse group,  // Inclus les détails de Group

        ResponsibleResponse responsible  // Inclus les détails de Responsible
        */

) {

}
