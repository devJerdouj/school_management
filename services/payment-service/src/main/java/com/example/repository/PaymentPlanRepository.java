package com.example.repository;

import com.example.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
}
