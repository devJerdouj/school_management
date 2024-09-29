package com.example.dto;

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
    private LocalDate dueDate;
    private Boolean isPaid;
    private LocalDate paymentDate;
}