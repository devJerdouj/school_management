package com.example.controller;

import com.example.dto.PaymentPhaseDto;
import com.example.service.PaymentPhaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payment-phases")
@AllArgsConstructor
public class PaymentPhaseController {

    private PaymentPhaseService paymentPhaseService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPaymentPhases(@RequestParam Long studentId, @RequestParam Long paymentPlanId) {
        paymentPhaseService.generatePaymentPhases(studentId, paymentPlanId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PaymentPhaseDto>> getPaymentPhasesByStudentId(@PathVariable Long studentId) {
        List<PaymentPhaseDto> paymentPhases = paymentPhaseService.getPaymentPhasesByStudentId(studentId);
        return new ResponseEntity<>(paymentPhases, HttpStatus.OK);
    }

    @GetMapping("/period")
    public ResponseEntity<List<PaymentPhaseDto>> getPaymentPhasesForPeriod(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<PaymentPhaseDto> paymentPhases = paymentPhaseService.getPaymentPhasesForPeriod(startDate, endDate);
        return new ResponseEntity<>(paymentPhases, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/next-unpaid")
    public ResponseEntity<PaymentPhaseDto> getNextUnpaidPaymentPhaseByStudentId(@PathVariable Long studentId) {
        PaymentPhaseDto paymentPhaseDto = paymentPhaseService.getNextUnpaidPaymentPhaseByStudentId(studentId);
        return new ResponseEntity<>(paymentPhaseDto, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/unpaid")
    public ResponseEntity<List<PaymentPhaseDto>> getUnpaidPaymentPhasesByStudentId(@PathVariable Long studentId) {
        List<PaymentPhaseDto> unpaidPhases = paymentPhaseService.getUnpaidPaymentPhasesByStudentId(studentId);
        return new ResponseEntity<>(unpaidPhases, HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<PaymentPhaseDto>> getOverduePaymentPhases() {
        List<PaymentPhaseDto> overduePhases = paymentPhaseService.getOverduePaymentPhases();
        return new ResponseEntity<>(overduePhases, HttpStatus.OK);
    }

    @PutMapping("/{paymentPhaseId}")
    public ResponseEntity<PaymentPhaseDto> updatePaymentPhase(@PathVariable Long paymentPhaseId, @RequestBody PaymentPhaseDto paymentPhaseDto) {
        PaymentPhaseDto updatedPhase = paymentPhaseService.updatePaymentPhase(paymentPhaseId, paymentPhaseDto);
        return new ResponseEntity<>(updatedPhase, HttpStatus.OK);
    }

    @GetMapping("/payment-plan/{paymentPlanId}")
    public ResponseEntity<List<PaymentPhaseDto>> getPaymentPhasesByPaymentPlanId(@PathVariable Long paymentPlanId) {
        List<PaymentPhaseDto> paymentPhases = paymentPhaseService.getPaymentPhasesByPaymentPlanId(paymentPlanId);
        return new ResponseEntity<>(paymentPhases, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentPhaseId}")
    public ResponseEntity<Void> deletePaymentPhase(@PathVariable Long paymentPhaseId) {
        paymentPhaseService.deletePaymentPhase(paymentPhaseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
