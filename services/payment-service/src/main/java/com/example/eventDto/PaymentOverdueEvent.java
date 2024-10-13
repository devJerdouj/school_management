package com.example.eventDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOverdueEvent {
    private Long paymentId;
    private Long studentId;
    private Double overdueAmount;
    private LocalDate dueDate;
}
