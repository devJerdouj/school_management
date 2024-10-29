package com.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@NoArgsConstructor
@Data
public class PaymentPhaseDto {

    private Long paymentPhaseId;
    private Long studentId;
    private Double amountDue;
    private Double remainingAmount;
    private Long paymentPlanId;
    private LocalDate dueDate;
    private Boolean isPaid;

    public PaymentPhaseDto(Long paymentPhaseId, Long studentId, Double amountDue, Long paymentPlanId, LocalDate dueDate, Boolean isPaid) {
        this.paymentPhaseId = paymentPhaseId;
        this.studentId = studentId;
        this.amountDue = amountDue;
        this.paymentPlanId = paymentPlanId;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
    }
}