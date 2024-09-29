package com.example.service;

import com.example.dto.PaymentRequest;
import com.example.entity.Payment;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import com.example.entity.PaymentStatus;
import com.example.repository.PaymentPhaseRepository;
import com.example.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    private PaymentPhaseRepository paymentPhaseRepository;


    public Payment createPayment(PaymentRequest paymentRequest) {

        Long studentId = 100L ;

        PaymentPhase unpaidPhase = paymentPhaseRepository.findByStudentIdAndIsPaidFalseOrderByDueDateAsc(studentId)
            .orElseThrow(() -> new IllegalArgumentException("No unpaid payment phases found for the student"));

        Double amountToPay = paymentRequest.getAmount();

        while (amountToPay > 0 && unpaidPhase != null) {
            if (amountToPay > unpaidPhase.getRemainingAmount()) {
                amountToPay -= unpaidPhase.getRemainingAmount();
                unpaidPhase.setRemainingAmount(0.0);
                unpaidPhase.setPaymentDate(LocalDate.now());
                unpaidPhase.setIsPaid(true);
            } else {
                unpaidPhase.setRemainingAmount(unpaidPhase.getRemainingAmount() - amountToPay);
                amountToPay = 0.0;
            }
            unpaidPhase = paymentPhaseRepository.save(unpaidPhase);

            if (amountToPay > 0) {
                unpaidPhase = paymentPhaseRepository.findByStudentIdAndIsPaidFalseOrderByDueDateAsc(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("No unpaid payment phases found for the student"));;
            }
        }

        Payment payment = new Payment(null, studentId, unpaidPhase, LocalDate.now(), amountToPay,
            paymentRequest.getPaymentMethod(), PaymentStatus.CREATED , new byte[0]);

        paymentRepository.save(payment);

        return payment;
    }

}
