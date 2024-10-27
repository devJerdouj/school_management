package com.example.service;

import com.example.client.StudentServiceClient;
import com.example.dto.PaymentPhaseDto;
import com.example.dto.PaymentRequest;
import com.example.entity.Payment;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import com.example.entity.PaymentStatus;
import com.example.eventDto.NextPaymentAlert;
import com.example.eventDto.PaymentCompletedEvent;
import com.example.eventDto.PaymentOverdueEvent;
import com.example.mapper.UpcomingUnpaidPhasesEventMapper;
import com.example.exception.ResourceNotFoundException;
import com.example.kafka.PaymentProducer;
import com.example.mapper.PaymentMapper;
import com.example.mapper.PaymentPhaseMapper;
import com.example.repository.PaymentPhaseRepository;
import com.example.repository.PaymentPlanRepository;
import com.example.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentPlanRepository paymentPlanRepository;
    private PaymentRepository paymentRepository;
    private PaymentPhaseService paymentPhaseService;
    private WebClient webClient;
    private StudentServiceClient studentServiceClient;
    private PaymentProducer paymentProducer;
    private PaymentPhaseRepository paymentPhaseRepository;
    private UpcomingUnpaidPhasesEventMapper upcomingUnpaidPhasesEventMapper;


    public void createPayment(PaymentRequest paymentRequest) {

        Long studentId = paymentRequest.getStudentId();
        if(!studentServiceClient.checkStudentValidity(studentId)){
            return;
        }

        PaymentPhaseDto nextUnpaidPaymentPhase =
                paymentPhaseService.getNextUnpaidPaymentPhaseByStudentId(studentId);
        PaymentPlan paymentPlan = paymentPlanRepository.findById(nextUnpaidPaymentPhase.getPaymentPlanId()).get();
        PaymentPhase unpaidPhase = PaymentPhaseMapper.toEntity(
                nextUnpaidPaymentPhase, paymentPlan);
        Double amountToPay = paymentRequest.getAmount();

        while (amountToPay > 0 && unpaidPhase != null) {
            if (amountToPay > unpaidPhase.getRemainingAmount()) {
                Payment payment = new Payment(null, studentId, unpaidPhase, LocalDate.now(), amountToPay,
                        paymentRequest.getPaymentMethod(), PaymentStatus.CREATED , new byte[0]);
                paymentRepository.save(payment);
                amountToPay -= unpaidPhase.getRemainingAmount();
                unpaidPhase.setRemainingAmount(0.0);
                unpaidPhase.setPaymentDate(LocalDate.now());

            } else {
                unpaidPhase.setRemainingAmount(unpaidPhase.getRemainingAmount() - amountToPay);

                Payment payment = new Payment(null, studentId, unpaidPhase, LocalDate.now(), amountToPay,
                        paymentRequest.getPaymentMethod(), PaymentStatus.CREATED , new byte[0]);

                paymentRepository.save(payment);

                amountToPay = 0.0;
            }
            paymentPhaseService.updatePaymentPhase(unpaidPhase.getPaymentPhaseId(),
                    PaymentPhaseMapper.toDto(unpaidPhase));


            if (amountToPay > 0) {
                unpaidPhase = PaymentPhaseMapper.toEntity(
                        nextUnpaidPaymentPhase, paymentPlan);
            }
        }

    }


    public Payment validatePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.VALIDATED);
        paymentRepository.save(payment);
        PaymentPhase paymentPhase = payment.getPaymentPhase();
        if(paymentPhase.getRemainingAmount()==0){
            paymentPhase.setIsPaid(true);
        }
        Long paymentPlanId = paymentPhase.getPaymentPlan().getPaymentPlanId();
        PaymentPhaseDto dto = PaymentPhaseMapper.toDto(paymentPhase);
        paymentPhaseService.updatePaymentPhase(paymentPlanId, dto);

        PaymentCompletedEvent completedEvent = new PaymentCompletedEvent(
                payment.getPaymentId(),
                payment.getStudentId(),
                payment.getAmount(),
                payment.getPaymentDate()
        );

        paymentProducer.sendPaymentCompletedEvent(completedEvent);

        return payment;
    }

    public List<PaymentRequest> getPaymentsByStudentId(Long studentId) {
        List<Payment> payments = paymentRepository.findAllByStudentIdOrderByPaymentDateDesc(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No payments found for this student"));
        return PaymentMapper.toDtoList(payments);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkUpcomingPayments(){

        LocalDate today = LocalDate.now();
        LocalDate expiredDate = today.plusDays(3);


        List<PaymentPhase> upcomingPaymentPhases = paymentPhaseRepository
                .findAllByDueDateAndIsPaidFalseOrderByDueDateAsc(expiredDate)
                .orElseThrow(() -> new EntityNotFoundException("No payment phases found for this due date"));

        Map<Long, List<PaymentPhase>> collect = upcomingPaymentPhases
                .stream().collect(Collectors.groupingBy(PaymentPhase::getStudentId));
        for (Long l : collect.keySet()) {
            var phases = collect.get(l);
            if(phases.size()>1 ){
                List<NextPaymentAlert> events = upcomingUnpaidPhasesEventMapper.mapToEvent(phases);
                paymentProducer.sendUpcomingPaymentReminderEvent(events);
            }else {
                NextPaymentAlert event = upcomingUnpaidPhasesEventMapper.mapToEvent(phases.get(0));
                paymentProducer.sendUpcomingPaymentReminderEvent(Collections.singletonList(event));
            }
        }




    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkOverduePayments() {
        LocalDate today = LocalDate.now();

        List<PaymentPhase> overduePaymentPhases = paymentPhaseRepository
                .findAllByDueDateBeforeAndIsPaidFalse(today);

        for (PaymentPhase phase : overduePaymentPhases) {
            PaymentOverdueEvent overdueEvent = new PaymentOverdueEvent(
                    phase.getPaymentPhaseId(),
                    phase.getStudentId(),
                    phase.getRemainingAmount(),
                    phase.getDueDate()
            );

            paymentProducer.sendPaymentOverdueEvent(overdueEvent);
        }
    }



}
