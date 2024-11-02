package com.example.eventDto;


import com.example.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {
    private Long paymentPhaseId;
    private String studentCode;
    private String studentFirstname;
    private String studentLastname;
    private String responsibleFirstname;
    private String responsibleEmail;
    private PaymentMethod paymentMethod;
    private Double amountPaid;
    private LocalDate paymentDate;
}
