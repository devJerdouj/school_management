package com.example.repository;

import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentPhaseRepository extends JpaRepository<PaymentPhase, Long> {

    Optional<PaymentPhase> findFirstByStudentIdAndIsPaidFalseOrderByDueDateAsc(Long id);
    Optional<List<PaymentPhase>> findAllByStudentIdAndIsPaidFalseOrderByDueDateAsc(Long id);
    Optional<List<PaymentPhase>> findAllByStudentIdOrderByDueDateAsc(Long id);
    Optional<List<PaymentPhase>> findAllByDueDateBeforeAndDueDateAfterOrderByDueDateAsc(LocalDate before, LocalDate after);
    Optional<List<PaymentPhase>> findAllByDueDateBeforeAndIsPaidFalseOrderByDueDateAsc(LocalDate today);
    Optional<List<PaymentPhase>> findAllByPaymentPlanOrderByDueDateAsc(PaymentPlan paymentPlan);
    Optional<List<PaymentPhase>> findAllByDueDateAndIsPaidFalseOrderByDueDateAsc(LocalDate date);

    List<PaymentPhase> findAllByDueDateBetweenAndIsPaidFalse(LocalDate today, LocalDate reminderDate);

    List<PaymentPhase> findAllByDueDateBeforeAndIsPaidFalse(LocalDate today);
}
