package com.example.mapper;

import com.example.dto.PaymentPlanRequest;
import com.example.entity.PaymentPlan;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentPlanMapper {

    public static PaymentPlanRequest toDto(PaymentPlan paymentPlan) {
        PaymentPlanRequest dto = new PaymentPlanRequest();
        dto.setPaymentPlanId(paymentPlan.getPaymentPlanId());
        dto.setAnnualCost(paymentPlan.getAnnualCost());
        dto.setNumberOfPhases(paymentPlan.getNumberOfPhases());
        return dto;
    }

    public static PaymentPlan toEntity(PaymentPlanRequest paymentPlanDto) {
        PaymentPlan paymentPlan = new PaymentPlan();
        paymentPlan.setAnnualCost(paymentPlanDto.getAnnualCost());
        paymentPlan.setNumberOfPhases(paymentPlanDto.getNumberOfPhases());
        return paymentPlan;
    }

    public static List<PaymentPlanRequest> toDtoList(List<PaymentPlan> paymentPlans) {
        return paymentPlans.stream()
                .map(PaymentPlanMapper::toDto)
                .collect(Collectors.toList());
    }
}
