package com.example.eventDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOverdueEvent {
    private Long phasePaymentId;
    private String studentCode;
    private String studentFirstname;
    private String studentLastname;
    private String responsibleFirstname;
    private String responsibleEmail;
    private Double overdueAmount;
    private LocalDate dueDate;
}
