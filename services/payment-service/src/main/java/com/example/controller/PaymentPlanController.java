package com.example.controller;

import com.example.dto.PaymentPlanRequest;
import com.example.service.PaymentPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/payment-plans")
@AllArgsConstructor
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;

    // Create a new Payment Plan
    @PostMapping("create")
    public ResponseEntity<PaymentPlanRequest> createPaymentPlan(@RequestBody PaymentPlanRequest paymentPlanDto) {
        PaymentPlanRequest createdPlan = paymentPlanService.createPaymentPlan(paymentPlanDto);
        return ResponseEntity.ok(createdPlan);
    }

    // Get a Payment Plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentPlanRequest> getPaymentPlanById(@PathVariable Long id) {
        PaymentPlanRequest paymentPlan = paymentPlanService.getPaymentPlanById(id);
        return ResponseEntity.ok(paymentPlan);
    }

    // Get all Payment Plans
    @GetMapping
    public ResponseEntity<List<PaymentPlanRequest>> getAllPaymentPlans() {
        List<PaymentPlanRequest> paymentPlans = paymentPlanService.getAllPaymentPlans();
        return ResponseEntity.ok(paymentPlans);
    }

    // Update an existing Payment Plan
    @PutMapping("/{id}")
    public ResponseEntity<PaymentPlanRequest> updatePaymentPlan(
            @PathVariable Long id,
            @RequestBody PaymentPlanRequest paymentPlanDto) {
        PaymentPlanRequest updatedPlan = paymentPlanService.updatePaymentPlan(id, paymentPlanDto);
        return ResponseEntity.ok(updatedPlan);
    }

    // Delete a Payment Plan by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentPlan(@PathVariable Long id) {
        paymentPlanService.deletePaymentPlan(id);
        return ResponseEntity.noContent().build();
    }

    // Get Payment Plans by Student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PaymentPlanRequest>> getPaymentPlansByStudentId(@PathVariable Long studentId) {
        List<PaymentPlanRequest> paymentPlans = paymentPlanService.getPaymentPlansByStudentId(studentId);
        return ResponseEntity.ok(paymentPlans);
    }
}