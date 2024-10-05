package com.example.schoolManagement.level;

import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.groupe.GroupRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record LevelRequest(

        Long id,

        @NotBlank(message = "Level name is required")
        @Size(min = 2, max = 50, message = "Level name must be between 2 and 50 characters")
        String name,

        @Min(value = 0, message = "Total cost cannot be negative")
        double totalCost,

        @NotNull(message = "Groups are required")
        Set<@Valid GroupRequest> groups
) {
}
