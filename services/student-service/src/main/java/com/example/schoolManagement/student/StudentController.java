package com.example.schoolManagement.student;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService ;

    @PostMapping()
    public ResponseEntity<Long> createStudent(
            @RequestBody @Valid  StudentRequest request , Long phasesNumber
    ){
        return ResponseEntity.ok(studentService.createStudent(request,phasesNumber));
    }

    @GetMapping("/{student-id}")
    public ResponseEntity<StudentResponse> findById(
            @PathVariable("student-id") Long studentId
    ){
        return ResponseEntity.ok(studentService.findById(studentId));
    }

    @GetMapping()
    public ResponseEntity<List<StudentResponse>> findAll(){
        return ResponseEntity.ok(studentService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        StudentRequest updatedRequest = new StudentRequest(
                id,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.code(),
                request.address(),
                request.numberPhone(),
                request.birthDate(),
                request.levelId(),
                request.groupId(),
                request.responsibleId(),
                request.paymentPlanId()
        );

        Long updatedStudentId = studentService.updateStudent(updatedRequest);
        return ResponseEntity.ok(updatedStudentId);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByGroupId(@PathVariable Long groupId) {
        List<StudentResponse> students = studentService.getAllStudentsByGroupId(groupId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/responsible/{responsibleId}")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByResponsibleId(@PathVariable Long responsibleId) {
        List<StudentResponse> students = studentService.getAllStudentsByResponsibleId(responsibleId);
        return ResponseEntity.ok(students);
    }





}
