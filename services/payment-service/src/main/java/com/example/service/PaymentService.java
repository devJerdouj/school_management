package com.example.service;

import com.example.dto.PaymentPhaseDto;
import com.example.dto.PaymentRequest;
import com.example.entity.Payment;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import com.example.entity.PaymentStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.PaymentPhaseRepository;
import com.example.repository.PaymentPlanRepository;
import com.example.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentPlanRepository paymentPlanRepository;
    private PaymentRepository paymentRepository;

    private PaymentPhaseRepository paymentPhaseRepository;

    public PaymentService(PaymentPlanRepository paymentPlanRepository) {
        this.paymentPlanRepository = paymentPlanRepository;
    }


    public void createPayment(PaymentRequest paymentRequest) {

        Long studentId = paymentRequest.getStudentId(); ;

        PaymentPhase unpaidPhase = paymentPhaseRepository.findFirstByStudentIdAndIsPaidFalseOrderByDueDateAsc(studentId)
            .orElseThrow(() -> new IllegalArgumentException("No unpaid payment phases found for the student"));

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
            unpaidPhase = paymentPhaseRepository.save(unpaidPhase);


            if (amountToPay > 0) {
                unpaidPhase = paymentPhaseRepository.findFirstByStudentIdAndIsPaidFalseOrderByDueDateAsc(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("No unpaid payment phases found for the student"));;
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
        paymentPhaseRepository.save(paymentPhase);

        return payment;
    }

    public void createPaymentPhases(Long studentId, Long paymentPlanId){
        PaymentPlan paymentPlan = paymentPlanRepository.findById(paymentPlanId).get();
        Integer numberOfPhases = paymentPlan.getNumberOfPhases();
        Double annualCost = paymentPlan.getAnnualCost();
        for (int i = 0; i < numberOfPhases; i++) {
            PaymentPhaseDto paymentPhaseDto =
                    new PaymentPhaseDto(studentId, annualCost / numberOfPhases,
                            LocalDate.of(2024, 1,1).plusMonths(((long) i * (int)(9/numberOfPhases)))
                            ,false,null);

            paymentPhaseRepository.save(mapper.topaymentPhase(paymentPhaseDto));
        }
    }



}
