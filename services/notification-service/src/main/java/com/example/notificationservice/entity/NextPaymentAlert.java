package com.example.notificationservice.entity;

import java.time.LocalDate;

public record NextPaymentAlert(
    Long PaymentPhaseId,
    String studentFirstname,
    String studentLastname,
    String responsibleFirstname,
    String responsibleEmail,
    Double amount,
    LocalDate dueDate
){}