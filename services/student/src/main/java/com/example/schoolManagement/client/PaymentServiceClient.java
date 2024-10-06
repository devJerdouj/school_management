package com.example.schoolManagement.client;

import com.example.schoolManagement.dto.PaymentPlanDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceClient {
    private final WebClient webClient;

    public PaymentPlanDto getPaymentPlanById(Long paymentPlanId) {
        return webClient.get()
                .uri("/api/payment-plans/{paymentPlanId}", paymentPlanId)
                .retrieve()
                .bodyToMono(PaymentPlanDto.class)
                .block();  // Utilisation synchrone ici
    }

    public void generatePaymentPhases(Long studentId, Long paymentPlanId) {
        webClient.post()
                .uri("/api/payment-phases/generate")
                .bodyValue(new GeneratePaymentPhasesRequest(studentId, paymentPlanId))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
