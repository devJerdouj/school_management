package org.techie.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@Data
public class Notification{
    @Id
    private String notificationId;
    private NotificationType notificationType;
    private LocalDateTime notificationDate;
    private PaymentConfirmation paymentConfirmation;
    private NextPaymentAlert nextPaymentAlert;

}
