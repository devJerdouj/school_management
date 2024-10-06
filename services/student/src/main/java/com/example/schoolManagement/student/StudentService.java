package com.example.schoolManagement.student;


import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.groupe.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;

    public  StudentResponse findById(Long studentId) {
        return studentRepository.findById(studentId)
                .map(studentMapper::toStudentResponse)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with the ID ::"+ studentId));

    }

    public  List<StudentResponse> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());
    }

    public Long createStudent(StudentRequest request, Long phasesNumber) {

        Student student = studentMapper.toStudent(request);

        if (request.groupId() == null) {
            Set<Group> availableGroups = groupRepository.findByLevelId(request.levelId());
            Group selectedGroup = availableGroups.stream()
                    .filter(group -> group.getStudentsNumber() < group.getGroupCapacity())
                    .min((a, b) -> a.getGroupCapacity() - b.getGroupCapacity())
                    .orElseThrow(() -> new IllegalStateException("No available groups in the specified level"));


            student.setGroup(selectedGroup);
        }

        // Gestion du PaymentPlan
        if (request.paymentPlanId() != null) {
            // Utiliser la couche service pour obtenir le PaymentPlan
            PaymentPlanDto paymentPlan = paymentPlanService.getPaymentPlanById(request.paymentPlanId());

            // Appeler la méthode generatePaymentPhases du service Payment
            paymentService.generatePaymentPhases(student.getId(), paymentPlan.getId(), paymentPlan.getNumberOfPhases());
        } else {
            // Si paymentPlanId est null, on génère un PaymentPlan basé sur le niveau de l'étudiant
            double totalCost = levelService.getAmountByStudentLevel(student.getId());

            // Création d'un PaymentPlan si nécessaire
            PaymentPlanDto newPaymentPlan = paymentPlanService.createPaymentPlan(
                    new PaymentPlanDto(null, totalCost, phasesNumber));

            // Appeler la méthode generatePaymentPhases avec le nouveau PaymentPlan
            paymentService.generatePaymentPhases(student.getId(), newPaymentPlan.getId(), phasesNumber);
        }


        return studentRepository.save(student).getId();
    }

    public Long updateStudent(StudentRequest request) {
        var student =studentMapper.toStudent(request);
        return studentRepository.save(student).getId();
    }
}
