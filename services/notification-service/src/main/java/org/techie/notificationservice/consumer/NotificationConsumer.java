package org.techie.notificationservice.consumer;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.techie.notificationservice.entity.*;
import org.techie.notificationservice.repository.NotificationRepository;
import org.techie.notificationservice.service.EmailService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationConsumer {

    private NotificationRepository notificationRepository;
    private EmailService emailService;

    @KafkaListener(topics = "payment-completed")
    public void consumePaymentCompleted(PaymentConfirmation confirmation) throws MessagingException {
        Notification notification = Notification.builder()
                .paymentConfirmation(confirmation)
                .notificationDate(LocalDateTime.now())
                .notificationType(NotificationType.PaymentConfirmation)
                .build();
        notificationRepository.save(notification);

        emailService.sendPaymentComplete(
                confirmation.responsibleEmail(),
                confirmation.responsibleFirstname(),
                confirmation.studentFirstname() + confirmation.studentLastname(),
                confirmation.amountPaid(),
                confirmation.PaymentPhaseId());
    }

    @KafkaListener(topics = "next-payment")
    public void consumeNextPaymentAlert(NextPaymentAlert alert) throws MessagingException {
        Notification notification = Notification.builder()
                .nextPaymentAlert(alert)
                .notificationDate(LocalDateTime.now())
                .notificationType(NotificationType.NextPaymentAlert)
                .build();
        notificationRepository.save(notification);

        emailService.sendNextPaymentAlert(
                alert.responsibleEmail(),
                alert.responsibleFirstname(),
                alert.studentFirstname() + alert.studentLastname(),
                alert.amount(),
                alert.dueDate(),
                alert.PaymentPhaseId());
    }

    @KafkaListener(topics = "payment-overdue")
    public void consumePaymentOverdueAlert(PaymentOverdueEvent alert) throws MessagingException {
        Notification notification = Notification.builder()
                .paymentOverdueEvent(alert)
                .notificationDate(LocalDateTime.now())
                .notificationType(NotificationType.PaymentOverdueAlert)
                .build();
        notificationRepository.save(notification);

        emailService.sendPaymentOverdueAlert(
                alert.responsibleEmail(),
                alert.responsibleFirstname(),
                alert.studentFirstname() + alert.studentLastname(),
                alert.overdueAmount(),
                alert.dueDate(),
                alert.phasePaymentId());
    }


    }
