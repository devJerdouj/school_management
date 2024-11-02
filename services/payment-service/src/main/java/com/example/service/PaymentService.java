package com.example.service;

import com.example.client.StudentServiceClient;
import com.example.dto.PaymentPhaseDto;
import com.example.dto.PaymentRequest;
import com.example.dto.StudentDto;
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
import org.springframework.transaction.annotation.Transactional;
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


    @Transactional
    public void createPayment(PaymentRequest paymentRequest) {

        Long studentId = paymentRequest.getStudentId();
        if(!studentServiceClient.checkStudentValidity(studentId)){
            return;
        }
        StudentDto studentDto = studentServiceClient.findStudentByID(studentId);

        PaymentPhaseDto nextUnpaidPaymentPhase =
                paymentPhaseService.getNextUnpaidPaymentPhaseByStudentId(studentId);
        PaymentPlan paymentPlan = paymentPlanRepository.findById(nextUnpaidPaymentPhase.getPaymentPlanId()).get();
        PaymentPhase unpaidPhase = PaymentPhaseMapper.toEntity(
                nextUnpaidPaymentPhase, paymentPlan);
        Double amountToPay = paymentRequest.getAmount();

        while (amountToPay > 0 ) {
            Payment payment ;
            if (amountToPay > unpaidPhase.getRemainingAmount()) {
                payment = new Payment(null, studentId, unpaidPhase, LocalDate.now(), unpaidPhase.getRemainingAmount(),
                        paymentRequest.getPaymentMethod(), PaymentStatus.CREATED , new byte[0]);
                amountToPay -= unpaidPhase.getRemainingAmount();
                unpaidPhase.setRemainingAmount((double) 0);
                unpaidPhase.setIsPaid(true);
                unpaidPhase.setPaymentDate(LocalDate.now());

            } else {
                unpaidPhase.setRemainingAmount(unpaidPhase.getRemainingAmount() - amountToPay);

                payment = new Payment(null, studentId, unpaidPhase, LocalDate.now(), amountToPay,
                        paymentRequest.getPaymentMethod(), PaymentStatus.CREATED , new byte[0]);

                amountToPay = 0.0;
            }
            paymentPhaseService.updatePaymentPhase(unpaidPhase.getPaymentPhaseId(),
                    PaymentPhaseMapper.toDto(unpaidPhase));

            paymentRepository.save(payment);

            System.out.println(nextUnpaidPaymentPhase);

            paymentProducer.sendPaymentCompletedEvent(new PaymentCompletedEvent(unpaidPhase.getPaymentPhaseId(),studentDto.code(),
                    studentDto.firstName(), studentDto.lastName(), studentDto.responsibleFirstname(),
                    studentDto.responsibleEmail(), paymentRequest.getPaymentMethod(),
                    payment.getAmount(), payment.getPaymentDate()));

            if (amountToPay > 0) {
                nextUnpaidPaymentPhase =
                        paymentPhaseService.getNextUnpaidPaymentPhaseByStudentId(studentId);
                paymentPlan = paymentPlanRepository.findById(nextUnpaidPaymentPhase.getPaymentPlanId()).get();
                unpaidPhase = PaymentPhaseMapper.toEntity(nextUnpaidPaymentPhase, paymentPlan);
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
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).code(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).firstName(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).lastName(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).responsibleFirstname(),
                studentServiceClient.findStudentByID(paymentPhase.getStudentId()).firstName(),
                payment.getPaymentMethod(),
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
//                List<NextPaymentAlert> events = upcomingUnpaidPhasesEventMapper.mapToEvent(phases);
//                paymentProducer.sendUpcomingPaymentReminderEvent(events);
            }else {
//                NextPaymentAlert event = upcomingUnpaidPhasesEventMapper.mapToEvent(phases.get(0));
//                paymentProducer.sendUpcomingPaymentReminderEvent(Collections.singletonList(event));
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
                    studentServiceClient.findStudentByID(phase.getStudentId()).code(),
                    studentServiceClient.findStudentByID(phase.getStudentId()).firstName(),
                    studentServiceClient.findStudentByID(phase.getStudentId()).lastName(),
                    studentServiceClient.findStudentByID(phase.getStudentId()).responsibleFirstname(),
                    studentServiceClient.findStudentByID(phase.getStudentId()).firstName(),                    phase.getRemainingAmount(),
                    phase.getDueDate()
            );

//            paymentProducer.sendPaymentOverdueEvent(overdueEvent);
        }
    }



}
