package com.example.service;

import com.example.dto.PaymentPhaseDto;
import com.example.entity.PaymentPhase;
import com.example.entity.PaymentPlan;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.PaymentPhaseMapper;
import com.example.repository.PaymentPhaseRepository;
import com.example.repository.PaymentPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentPhaseService {

    private PaymentPlanRepository paymentPlanRepository;
    private PaymentPhaseRepository paymentPhaseRepository;

    public void createPaymentPhases(Long studentId, Long paymentPlanId){
        PaymentPlan paymentPlan = paymentPlanRepository.findById(paymentPlanId).get();
        Integer numberOfPhases = paymentPlan.getNumberOfPhases();
        Double annualCost = paymentPlan.getAnnualCost();
        for (int i = 0; i < numberOfPhases; i++) {
            PaymentPhaseDto paymentPhaseDto =
                    new PaymentPhaseDto(studentId, annualCost / numberOfPhases,
                            paymentPlanId, LocalDate.of(2024, 1,1).plusMonths(((long) i * (int)(9/numberOfPhases)))
                            ,false);

            paymentPhaseRepository.save(PaymentPhaseMapper.toEntity(paymentPhaseDto, paymentPlan));
        }
    }

    public List<PaymentPhaseDto> getPaymentPhasesByStudentId(Long studentId){
        List<PaymentPhase> paymentPhases = paymentPhaseRepository.findAllByStudentIdOrderByDueDateAsc(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No payment phase found for this student"));
        return PaymentPhaseMapper.toDtoList(paymentPhases);
    }

    public List<PaymentPhaseDto> getPaymentPhasesForPeriod(LocalDate startDate, LocalDate endDate){
        List<PaymentPhase> paymentPhases =
                paymentPhaseRepository.findAllByDueDateBeforeAndDueDateAfterOrderByDueDateAsc(endDate, startDate)
                .orElseThrow(() -> new ResourceNotFoundException("No payment phase found for this student"));
        return PaymentPhaseMapper.toDtoList(paymentPhases);
    }

    public PaymentPhaseDto getNextUnpaidPaymentPhaseByStudentId(Long studentId) {
        PaymentPhase nextUnpaidPhase = paymentPhaseRepository.findFirstByStudentIdAndIsPaidFalseOrderByDueDateAsc(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No unpaid payment phases found for this student"));
        return PaymentPhaseMapper.toDto(nextUnpaidPhase);
    }



    public List<PaymentPhaseDto> getUnpaidPaymentPhasesByStudentId(Long studentId) {
        List<PaymentPhase> unpaidPhases = paymentPhaseRepository.findAllByStudentIdAndIsPaidFalseOrderByDueDateAsc(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No unpaid payment phases found for this student"));
        return PaymentPhaseMapper.toDtoList(unpaidPhases);
    }

    public List<PaymentPhaseDto> getOverduePaymentPhases() {
        LocalDate today = LocalDate.now();
        List<PaymentPhase> overduePhases = paymentPhaseRepository.findAllByDueDateBeforeAndIsPaidFalseOrderByDueDateAsc(today)
                .orElseThrow(() -> new ResourceNotFoundException("No overdue payment phases found"));
        return PaymentPhaseMapper.toDtoList(overduePhases);
    }

    public PaymentPhaseDto updatePaymentPhase(Long paymentPhaseId, PaymentPhaseDto paymentPhaseDto){
        PaymentPhase paymentPhase = PaymentPhaseMapper.toEntity(paymentPhaseDto,
                paymentPlanRepository.findById(paymentPhaseDto.getPaymentPlanId())
                        .orElseThrow(() -> new IllegalArgumentException("No payment plan found with this id")));
        paymentPhaseRepository.save(paymentPhase);
        return paymentPhaseDto;
    }

    public List<PaymentPhaseDto> getPaymentPhasesByPaymentPlanId(Long paymentPlanId) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(paymentPlanId)
                .orElseThrow(() -> new IllegalArgumentException("No payment plan found with this id"));
        List<PaymentPhase> paymentPhases = paymentPhaseRepository.findAllByPaymentPlanOrderByDueDateAsc(paymentPlan)
                .orElseThrow(() -> new ResourceNotFoundException("No payment phases found for this payment plan"));
        return PaymentPhaseMapper.toDtoList(paymentPhases);
    }


    public void deletePaymentPhase(Long paymentPhaseId){
        PaymentPhase paymentPhase = paymentPhaseRepository.findById(paymentPhaseId)
                .orElseThrow(() -> new IllegalArgumentException("No payment phase found with this id"));
        paymentPhaseRepository.delete(paymentPhase);
    }


}
