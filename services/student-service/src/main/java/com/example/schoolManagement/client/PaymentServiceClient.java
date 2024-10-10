package com.example.schoolManagement.client;

import com.example.schoolManagement.dto.PaymentPlanDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class PaymentServiceClient {

    private final WebClient webClient;

    public PaymentPlanDto getPaymentPlanById(Long paymentPlanId) {
        return webClient.get()
                .uri("payments/api/payment-plans/{paymentPlanId}", paymentPlanId)
                .retrieve()
                .bodyToMono(PaymentPlanDto.class)
                .block();
    }

    public void generatePaymentPhases(Long studentId, Long paymentPlanId) {
        Object request = new Object() {
            public Long studentI = studentId;
            public Long paymentPlanI = paymentPlanId;
        };
        webClient.post()
                .uri("payments/api/payment-phases/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public PaymentPlanDto createPaymentPlan(PaymentPlanDto paymentPlanDto) {
        Object request = new Object() {
            Long paymentPlanId = paymentPlanDto.paymentPlanId();
            Double annualCost = paymentPlanDto.annualCost();
            Long numberOfPhases = paymentPlanDto.numberOfPhases();
        };
        return webClient.post()
                .uri("payments/api/payment-plans/create")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentPlanDto.class)
                .block();

    }
}
