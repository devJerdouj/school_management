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
    public ResponseEntity<Integer> createStudent(
            @RequestBody @Valid  StudentRequest request
    ){
        return ResponseEntity.ok(studentService.createStudent(request));
    }

    @GetMapping("/{student-id}")
    public ResponseEntity<StudentResponse> findById(
            @PathVariable("student-id") Integer studentId
    ){
        return ResponseEntity.ok(StudentService.findById(studentId));
    }

    @GetMapping()
    public ResponseEntity<List<StudentResponse>> findAll(){
        return ResponseEntity.ok(StudentService.findAll());
    }

}
