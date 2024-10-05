package com.example.service;

import com.example.dto.PaymentPhaseDto;
import com.example.dto.PaymentPlanRequest;
import com.example.entity.PaymentPlan;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.PaymentPlanMapper;
import com.example.repository.PaymentPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentPlanService {

    private final PaymentPlanRepository paymentPlanRepository;
    private final PaymentPhaseService paymentPhaseService;



    public PaymentPlanRequest createPaymentPlan(PaymentPlanRequest paymentPlanDto) {
        PaymentPlan paymentPlan = PaymentPlanMapper.toEntity(paymentPlanDto);
        paymentPlan = paymentPlanRepository.save(paymentPlan);
        return PaymentPlanMapper.toDto(paymentPlan);
    }

    public PaymentPlanRequest getPaymentPlanById(Long paymentPlanId) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(paymentPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Plan not found with ID: " + paymentPlanId));
        return PaymentPlanMapper.toDto(paymentPlan);
    }

    public List<PaymentPlanRequest> getAllPaymentPlans() {
        List<PaymentPlan> paymentPlans = paymentPlanRepository.findAll();
        return PaymentPlanMapper.toDtoList(paymentPlans);
    }

    public PaymentPlanRequest updatePaymentPlan(Long paymentPlanId, PaymentPlanRequest paymentPlanDto) {
        PaymentPlan existingPlan = paymentPlanRepository.findById(paymentPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Plan not found with ID: " + paymentPlanId));

        existingPlan.setAnnualCost(paymentPlanDto.getAnnualCost());
        existingPlan.setNumberOfPhases(paymentPlanDto.getNumberOfPhases());

        PaymentPlan updatedPlan = paymentPlanRepository.save(existingPlan);
        return PaymentPlanMapper.toDto(updatedPlan);
    }

    public void deletePaymentPlan(Long paymentPlanId) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(paymentPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Plan not found with ID: " + paymentPlanId));
        paymentPlanRepository.delete(paymentPlan);
    }

    public List<PaymentPlanRequest> getPaymentPlansByStudentId(Long studentId) {
        List<PaymentPlan> paymentPlans = new ArrayList<>();
        List<PaymentPhaseDto> paymentPhasesByStudentId = paymentPhaseService.getPaymentPhasesByStudentId(studentId);
        paymentPhasesByStudentId.forEach(
                p -> paymentPlans.add(paymentPlanRepository.findById( p.getPaymentPlanId()).get()));
        if(paymentPlans.isEmpty()){
            throw new ResourceNotFoundException("No payment plans found for student with ID: " + studentId);
        }
        return PaymentPlanMapper.toDtoList(paymentPlans);
    }
}
