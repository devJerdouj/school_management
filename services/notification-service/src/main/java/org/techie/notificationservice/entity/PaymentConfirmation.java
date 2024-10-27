package org.techie.notificationservice.entity;

import java.time.LocalDate;

public record PaymentConfirmation(
        Long PaymentPhaseId,
        String studentCode,
        String studentFirstname,
        String studentLastname,
        String responsibleFirstname,
        String responsibleEmail,
        PaymentMethod paymentMethod,
        Double amountPaid,
        LocalDate paymentDate
) {


}
