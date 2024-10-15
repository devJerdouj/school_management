package com.example.schoolManagement.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<List<Student>> findAllByGroupId(Long groupId);
    Optional<List<Student>> findAllByResponsibleId(Long responsibleId);
}
