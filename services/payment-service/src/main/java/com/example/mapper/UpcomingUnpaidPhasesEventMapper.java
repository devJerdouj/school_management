package com.example.mapper;

import com.example.entity.PaymentPhase;
import com.example.eventDto.NextPaymentAlert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpcomingUnpaidPhasesEventMapper {

    public static NextPaymentAlert mapToEvent(PaymentPhase paymentPhase){
        return new NextPaymentAlert(
                paymentPhase.getPaymentPhaseId(),
                paymentPhase.getStudentId(),
                paymentPhase.getDueDate(),
                paymentPhase.getRemainingAmount()
        );
    }

    public List<NextPaymentAlert>  mapToEvent(List<PaymentPhase> paymentPhases){
        List<NextPaymentAlert> events = new ArrayList<>();

        for (PaymentPhase paymentPhase : paymentPhases) {
            events.add(mapToEvent(paymentPhase));
        }
        return events;
    }



}
