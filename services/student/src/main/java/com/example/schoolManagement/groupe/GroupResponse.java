package com.example.schoolManagement.groupe;

public record GroupResponse(
        Long id,
        String name,
        int studentsNumber,
        int groupCapacity
) {
}
