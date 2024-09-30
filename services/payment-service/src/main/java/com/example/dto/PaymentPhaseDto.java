package com.example.dto;

import com.example.entity.PaymentPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentPhaseDto {

    private Long studentId;
    private Double amountDue;
    private Long paymentPlanId;
    private LocalDate dueDate;
    private Boolean isPaid;
}