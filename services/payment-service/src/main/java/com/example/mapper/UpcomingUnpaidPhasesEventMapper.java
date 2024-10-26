package com.example.mapper;

import com.example.entity.PaymentPhase;
import com.example.eventDto.PaymentOverdueEvent;
import com.example.eventDto.UpcomingPaymentReminderEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpcomingUnpaidPhasesEventMapper {

    public static UpcomingPaymentReminderEvent  mapToEvent(PaymentPhase paymentPhase){
        return new UpcomingPaymentReminderEvent(
                paymentPhase.getPaymentPhaseId(),
                paymentPhase.getStudentId(),
                paymentPhase.getDueDate(),
                paymentPhase.getRemainingAmount()
        );
    }

    public List<UpcomingPaymentReminderEvent>  mapToEvent(List<PaymentPhase> paymentPhases){
        List<UpcomingPaymentReminderEvent> events = new ArrayList<>();

        for (PaymentPhase paymentPhase : paymentPhases) {
            events.add(mapToEvent(paymentPhase));
        }
        return events;
    }



}
