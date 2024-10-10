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

}
