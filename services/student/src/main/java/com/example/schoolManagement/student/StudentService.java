package com.example.schoolManagement.student;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    public static StudentResponse findById(Integer studentId) {
        return null;
    }

    public static List<StudentResponse> findAll() {
    }

    public Integer createStudent(StudentRequest request) {
        return null;
    }
}
