package com.example.schoolManagement.student;


import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.groupe.GroupMapper;
import com.example.schoolManagement.groupe.GroupResponse;
import com.example.schoolManagement.level.Level;
import com.example.schoolManagement.level.LevelResponse;
import com.example.schoolManagement.responsible.Responsible;
import com.example.schoolManagement.responsible.ResponsibleResponse;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    private final GroupMapper groupMapper;

    public StudentMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }
    public Student toStudent(StudentRequest request) {
       return  Student.builder()
               .id(request.id())
               .firstName(request.firstName())
               .lastName(request.lastName())
               .address(request.address())
               .code(request.code())
               .email(request.email())
               .numberPhone(request.numberPhone())
               .birthDate(request.birthDate())
               .paymentPlanId(request.paymentPlanId())
               .group(Group.builder().id(request.groupId()).build())
               .level(Level.builder().id(request.levelId()).build())
               .responsible(Responsible.builder().id(request.responsibleId()).build())
               .build();
    }

    public StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getCode(),
                student.getAddress(),
                student.getNumberPhone(),
                student.getBirthDate(),
                student.getPaymentPlanId(),
                new LevelResponse(student.getLevel().getId(), student.getLevel().getName(),student.getLevel().getTotalCost(),groupMapper.toGroupResponseSet(student.getLevel().getGroups())),
                new GroupResponse(student.getGroup().getId(), student.getGroup().getName(),student.getGroup().getStudentsNumber(),student.getGroup().getGroupCapacity()),
                new ResponsibleResponse(student.getResponsible().getId(), student.getResponsible().getFirstName(), student.getResponsible().getLastName(), student.getResponsible().getPhoneNumber())
        );

    }
}
