package org.techie.notificationservice.entity;

public record PaymentConfirmation(
        Long PaymentPhaseId,
        String studentCode,
        String studentFirstname,
        String studentLastname,
        String responsibleFirstname,
        String responsibleEmail,
        PaymentMethod paymentMethod,
        Double amount
) {


}
