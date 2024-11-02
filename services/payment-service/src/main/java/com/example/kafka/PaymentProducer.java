package com.example.kafka;


import com.example.eventDto.NextPaymentAlert;
import com.example.eventDto.PaymentCompletedEvent;
import com.example.eventDto.PaymentOverdueEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {


    private static final String PAYMENT_COMPLETED_TOPIC = "payment-completed";
    private static final String PAYMENT_OVERDUE_TOPIC = "payment-overdue";
    private static final String UPCOMING_PAYMENT_REMINDER_TOPIC = "next-payment";


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("Sending PaymentCompletedEvent for paymentId: {}", event.getPaymentPhaseId());

        Message<PaymentCompletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, PAYMENT_COMPLETED_TOPIC)
                .build();

        try {
            kafkaTemplate.send(message);
            log.info("PaymentCompletedEvent sent successfully for paymentId: {}", event.getPaymentPhaseId());
        } catch (Exception e) {
            log.error("Failed to send PaymentCompletedEvent for paymentId: {}", event.getPaymentPhaseId(), e);
            throw new RuntimeException("Error while sending PaymentCompletedEvent", e);
        }
    }

    public void sendPaymentOverdueEvent(PaymentOverdueEvent event) {
        log.info("Sending PaymentOverdueEvent for paymentId: {}", event.getPhasePaymentId());

        Message<PaymentOverdueEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, PAYMENT_OVERDUE_TOPIC)
                .build();

        try {
            kafkaTemplate.send(message);
            log.info("PaymentOverdueEvent sent successfully for paymentId: {}", event.getPhasePaymentId());
        } catch (Exception e) {
            log.error("Failed to send PaymentOverdueEvent for paymentId: {}", event.getPhasePaymentId(), e);
            throw new RuntimeException("Error while sending PaymentOverdueEvent", e);
        }
    }

    public void sendUpcomingPaymentReminderEvent(List<NextPaymentAlert> event) {

              //log.info("Sending NextPaymentAlert for the student :: {}",
        //      event.getFirst().getStudentFirstname()+ " " + event.getFirst().getStudentLastname());

        Message<List<NextPaymentAlert>> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, UPCOMING_PAYMENT_REMINDER_TOPIC)
                .build();

        try {
            kafkaTemplate.send(message);
     //       log.info("NextPaymentAlert sent successfully for studentId: {}", event.getFirst().getStudentId());
        } catch (Exception e) {
       //     log.error("Failed to send NextPaymentAlert for studentId: {}", event.getFirst().getStudentId(), e);
            throw new RuntimeException("Error while sending NextPaymentAlert", e);
        }
    }
}