package com.example.repository;

import com.example.entity.PaymentPhase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentPhaseRepository extends JpaRepository<PaymentPhase, Long> {

    Optional<PaymentPhase> findByStudentIdAndIsPaidFalseOrderByDueDateAsc(Long id);
    Optional<List<PaymentPhase>> findAllByStudentIdOrderByDueDateAsc(Long id);

}
