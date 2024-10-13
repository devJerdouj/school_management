package com.example.kafka;


import com.example.eventDto.PaymentCompletedEvent;
import com.example.eventDto.PaymentOverdueEvent;
import com.example.eventDto.UpcomingPaymentReminderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private static final String PAYMENT_COMPLETED_TOPIC = "payment_completed";
    private static final String PAYMENT_OVERDUE_TOPIC = "payment_overdue";
    private static final String UPCOMING_PAYMENT_REMINDER_TOPIC = "upcoming_payment_reminder";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {
        try {
            kafkaTemplate.send(PAYMENT_COMPLETED_TOPIC, event);
            log.info("Sent PaymentCompletedEvent to Kafka: {}", event);
        } catch (Exception e) {
            log.error("Failed to send PaymentCompletedEvent to Kafka", e);
        }
    }

    public void sendPaymentOverdueEvent(PaymentOverdueEvent event) {
        try {
            kafkaTemplate.send(PAYMENT_OVERDUE_TOPIC, event);
            log.info("Sent PaymentOverdueEvent to Kafka: {}", event);
        } catch (Exception e) {
            log.error("Failed to send PaymentOverdueEvent to Kafka", e);
        }
    }

    public void sendUpcomingPaymentReminderEvent(UpcomingPaymentReminderEvent event) {
        try {
            kafkaTemplate.send(UPCOMING_PAYMENT_REMINDER_TOPIC, event);
            log.info("Sent UpcomingPaymentReminderEvent to Kafka: {}", event);
        } catch (Exception e) {
            log.error("Failed to send UpcomingPaymentReminderEvent to Kafka", e);
        }
    }
}
