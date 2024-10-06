package com.example.schoolManagement.dto;

import java.time.LocalDate;

public record PaymentPlanDto(
         Long paymentPlanId,
         Double annualCost,
         Integer numberOfPhases
) {}
