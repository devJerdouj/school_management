package com.example.schoolManagement.student;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
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

    public Long createStudent(StudentRequest request) {
        var student =studentMapper.toStudent(request);
        return studentRepository.save(student).getId();
    }
}
