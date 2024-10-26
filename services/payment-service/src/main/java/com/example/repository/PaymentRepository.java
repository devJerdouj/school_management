package com.example.repository;

import com.example.entity.Payment;
import com.example.entity.PaymentMethod;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentStatus;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Optional<List<Payment>> findAllByStudentIdOrderByPaymentDateDesc(Long studentId);
    Optional<List<Payment>> findAllByPaymentPhaseOrderByPaymentDateDesc(PaymentPhase paymentPhase);
    Optional<List<Payment>> findAllByPaymentDateBetweenOrderByPaymentDateAsc(LocalDate startDate, LocalDate endDate);
    Optional<List<Payment>> findAllByPaymentMethodOrderByPaymentDateDesc(PaymentMethod paymentMethod);
    Optional<List<Payment>> findAllByPaymentStatusOrderByPaymentDateDesc(PaymentStatus paymentStatus);
    Optional<Payment> findFirstByStudentIdOrderByPaymentDateDesc(Long studentId);
    Optional<List<Payment>> findAllByReceiptIsNotNullOrderByPaymentDateDesc();
    Optional<List<Payment>> findAllByReceiptIsNullOrderByPaymentDateDesc();

}
