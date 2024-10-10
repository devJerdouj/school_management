package com.example.schoolManagement.groupe;

import com.example.schoolManagement.level.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public record GroupRequest(


        Long id,

        @NotBlank(message = "Group name is required")
        @Size(min = 2, max = 50, message = "Group name must be between 2 and 50 characters")
        String name,

        @Min(value = 0, message = "Number of students cannot be negative")
        @Max(value = 1000, message = "Number of students exceeds the limit")
        int studentsNumber,

        @Min(value = 1, message = "Group capacity must be at least 1")
        @Max(value = 1000, message = "Group capacity exceeds the limit")
        int groupCapacity,

        @NotNull(message = "Level ID is required")
        Long levelId
) {
}
