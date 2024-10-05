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
        // payment plan id != null
        //      check payment plan existence || if not existed create new one
        //      call the generatePaymentPhases method of payment Service
        // payment plan == null
        //      call the getAmountBystudentlevel from the levelService class
        //      use paymentPhases param
        //      check payment plan existence || if not existed create new one
        //      call the generatePaymentPhases method of payment Service



        return studentRepository.save(student).getId();
    }

    public Long updateStudent(StudentRequest request) {
        var student =studentMapper.toStudent(request);
        return studentRepository.save(student).getId();
    }
}
