package com.example.schoolManagement.student;


import com.example.schoolManagement.client.PaymentServiceClient;
import com.example.schoolManagement.dto.PaymentPlanDto;
import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.groupe.GroupRepository;
import com.example.schoolManagement.level.LevelService;
import com.example.schoolManagement.responsible.ResponsibleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;
    private final PaymentServiceClient paymentService;
    private final LevelService levelService;

    public  StudentResponse findById(Long studentId) {
        StudentResponse studentResponse = studentRepository.findById(studentId)
                .map(studentMapper::toStudentResponse)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with the ID ::" + studentId));
        return studentResponse;
    }

    public  List<StudentResponse> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createStudent(StudentRequest request, Long phasesNumber) {


        Student student = studentMapper.toStudent(request);
        student.setId(null);

        if (request.groupId() == null) {
            Set<Group> availableGroups = groupRepository.findByLevelId(request.levelId());
            Group selectedGroup = availableGroups.stream()
                    .filter(group -> group.getStudentsNumber() < group.getGroupCapacity())
                    .min((a, b) -> a.getGroupCapacity() - b.getGroupCapacity())
                    .orElseThrow(() -> new IllegalStateException("No available groups in the specified level"));


            student.setGroup(selectedGroup);
        }

        PaymentPlanDto paymentPlan;
        if (request.paymentPlanId() != null) {
            paymentPlan = paymentService.getPaymentPlanById(request.paymentPlanId());

        } else {
            double totalCost = levelService.getAmountByStudentLevel(student.getId());

            paymentPlan = paymentService.createPaymentPlan(
                    new PaymentPlanDto(null, totalCost, phasesNumber));

        }

        Long id = studentRepository.save(student).getId();
        paymentService.generatePaymentPhases(id, paymentPlan.paymentPlanId());
        System.out.println(id);
        return id;

    }

    public Long updateStudent(StudentRequest request) {
        var student =studentMapper.toStudent(request);
        return studentRepository.save(student).getId();
    }

    public List<StudentResponse> getAllStudentsByGroupId(Long groupId) {

        Optional<List<Student>> optionalStudents = studentRepository.findAllByGroupId(groupId);

        List<Student> students = optionalStudents.orElseThrow(() ->
                new EntityNotFoundException("No students found for group ID: " + groupId));

        return students.stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getAllStudentsByResponsibleId(Long responsibleId) {
        Optional<List<Student>> optionalStudents = studentRepository.findAllByResponsibleId(responsibleId);

        List<Student> students = optionalStudents.orElseThrow(() ->
                new EntityNotFoundException("No students found for group ID: " + responsibleId));

        return students.stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());
    }

}
