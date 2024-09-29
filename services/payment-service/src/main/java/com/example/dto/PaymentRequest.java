package com.example.dto;

import com.example.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest {
    private Long studentId;
    private Double amount;
    private PaymentMethod paymentMethod;
}
