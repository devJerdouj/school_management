package com.example.notificationservice.entity;

import java.time.LocalDate;

public record PaymentCompletedEvent(
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
