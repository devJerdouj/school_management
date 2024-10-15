package com.example.eventDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingPaymentReminderEvent {
    private Long paymentPhaseId;
    private Long studentId;
    private LocalDate paymentDueDate;
    private Double amountDue;
}
