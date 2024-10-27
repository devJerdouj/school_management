package com.example.controller;

import com.example.dto.PaymentRequest;
import com.example.entity.Payment;
import com.example.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;



    @PostMapping("/create")
    public ResponseEntity<Void> createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{paymentId}/validate")
    public ResponseEntity<Payment> validatePayment(@PathVariable Long paymentId) {
        Payment validatedPayment = paymentService.validatePayment(paymentId);
        return new ResponseEntity<>(validatedPayment, HttpStatus.OK);
    }


    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PaymentRequest>> getPaymentsByStudentId(@PathVariable Long studentId) {
        List<PaymentRequest> payments = paymentService.getPaymentsByStudentId(studentId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
