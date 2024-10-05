package com.example.repository;

import com.example.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
    Optional<List<PaymentPlan>> findAllByStudentId(Long studentId);
}
