package com.example.mapper;

import com.example.client.StudentServiceClient;
import com.example.entity.PaymentPhase;
import com.example.eventDto.NextPaymentAlert;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UpcomingUnpaidPhasesEventMapper {

    private final StudentServiceClient studentServiceClient;

    public  NextPaymentAlert mapToEvent(PaymentPhase paymentPhase){
        return new NextPaymentAlert(
                paymentPhase.getPaymentPhaseId(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).firstName(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).lastName(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).responsibleFirstname(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).email(),
                paymentPhase.getRemainingAmount(),
                paymentPhase.getDueDate()
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
