package com.example.schoolManagement.student;


import com.example.schoolManagement.client.PaymentServiceClient;
import com.example.schoolManagement.dto.PaymentPlanDto;
import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.groupe.GroupRepository;
import com.example.schoolManagement.level.LevelService;
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
    private PaymentServiceClient paymentService;
    private LevelService levelService;

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

        if (request.paymentPlanId() != null) {
            PaymentPlanDto paymentPlan = paymentService.getPaymentPlanById(request.paymentPlanId());

            paymentService.generatePaymentPhases(student.getId(), paymentPlan.paymentPlanId());
        } else {
            double totalCost = levelService.getAmountByStudentLevel(student.getId());

            PaymentPlanDto newPaymentPlan = paymentService.createPaymentPlan(
                    new PaymentPlanDto(null, totalCost, phasesNumber));

            paymentService.generatePaymentPhases(student.getId(), newPaymentPlan.paymentPlanId());
        }

        return studentRepository.save(student).getId();
    }

    public Long updateStudent(StudentRequest request) {
        var student =studentMapper.toStudent(request);
        return studentRepository.save(student).getId();
    }
}
