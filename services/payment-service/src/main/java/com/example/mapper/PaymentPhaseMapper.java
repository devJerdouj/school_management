package com.example.mapper;

import com.example.dto.PaymentPhaseDto;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import java.util.List;
import java.util.stream.Collectors;


public class PaymentPhaseMapper {

    public static PaymentPhaseDto toDto(PaymentPhase paymentPhase) {
        PaymentPhaseDto dto = new PaymentPhaseDto();
        dto.setPaymentPhaseId(paymentPhase.getPaymentPhaseId());
        dto.setStudentId(paymentPhase.getStudentId());
        dto.setAmountDue(paymentPhase.getAmountDue());
        dto.setRemainingAmount(paymentPhase.getRemainingAmount());
        dto.setDueDate(paymentPhase.getDueDate());
        dto.setIsPaid(paymentPhase.getIsPaid());
        dto.setPaymentPlanId(paymentPhase.getPaymentPlan().getPaymentPlanId());
        return dto;
    }

    public static PaymentPhase toEntity(PaymentPhaseDto paymentPhaseDto, PaymentPlan paymentPlan) {
        PaymentPhase paymentPhase = new PaymentPhase();
        paymentPhase.setPaymentPhaseId(paymentPhaseDto.getPaymentPhaseId());
        paymentPhase.setStudentId(paymentPhaseDto.getStudentId());
        paymentPhase.setAmountDue(paymentPhaseDto.getAmountDue());
        paymentPhase.setDueDate(paymentPhaseDto.getDueDate());
        paymentPhase.setIsPaid(paymentPhaseDto.getIsPaid());
        paymentPhase.setRemainingAmount(paymentPhaseDto.getRemainingAmount());
        paymentPhase.setPaymentPlan(paymentPlan);

        return paymentPhase;
    }

    public static List<PaymentPhaseDto> toDtoList(List<PaymentPhase> paymentPhases) {
        return paymentPhases.stream()
                .map(PaymentPhaseMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<PaymentPhase> toEntityList(List<PaymentPhaseDto> paymentPhaseDtos, PaymentPlan paymentPlan) {
        return paymentPhaseDtos.stream()
                .map(dto -> toEntity(dto, paymentPlan))
                .collect(Collectors.toList());
    }
}