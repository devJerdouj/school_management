package com.example.service;

import com.example.dto.PaymentPhaseDto;
import com.example.dto.PaymentRequest;
import com.example.entity.Payment;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import com.example.entity.PaymentStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.PaymentMapper;
import com.example.mapper.PaymentPhaseMapper;
import com.example.repository.PaymentPlanRepository;
import com.example.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentPlanRepository paymentPlanRepository;
    private PaymentRepository paymentRepository;
    private PaymentPhaseService paymentPhaseService;

    public PaymentService(PaymentPlanRepository paymentPlanRepository) {
        this.paymentPlanRepository = paymentPlanRepository;
    }


    public void createPayment(PaymentRequest paymentRequest) {

        Long studentId = paymentRequest.getStudentId(); ;

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

        return payment;
    }

    public List<PaymentRequest> getPaymentsByStudentId(Long studentId) {
        List<Payment> payments = paymentRepository.findAllByStudentIdOrderByPaymentDateDesc(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No payments found for this student"));
        return PaymentMapper.toDtoList(payments);
    }





}
