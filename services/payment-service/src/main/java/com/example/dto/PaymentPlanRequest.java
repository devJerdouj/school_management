package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentPlanRequest {
    private Long paymentPlanId;
    private Double annualCost;
    private Integer numberOfPhases;
}
