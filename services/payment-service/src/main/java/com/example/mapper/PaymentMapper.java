package com.example.mapper;

import com.example.dto.PaymentRequest;
import com.example.entity.Payment;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {


    public static Payment toEntity(PaymentRequest paymentRequest, PaymentPhase paymentPhase) {
        Payment payment = new Payment();
        payment.setStudentId(paymentRequest.getStudentId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentPhase(paymentPhase);

        return payment;
    }

    public static PaymentRequest toDto(Payment payment) {
        PaymentRequest dto = new PaymentRequest();
        dto.setStudentId(payment.getStudentId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());

        return dto;
    }

    public static List<PaymentRequest> toDtoList(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Payment> toEntityList(List<PaymentRequest> paymentRequests, PaymentPhase paymentPhase) {
        return paymentRequests.stream()
                .map(dto -> toEntity(dto, paymentPhase))
                .collect(Collectors.toList());
    }
}
