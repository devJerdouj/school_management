package com.example.notificationservice.entity;

import java.time.LocalDate;

public record PaymentOverdueEvent(
        Long phasePaymentId,
        String studentCode,
        String studentFirstname,
        String studentLastname,
        String responsibleFirstname,
        String responsibleEmail,
        Double overdueAmount,
        LocalDate dueDate
) {
}
