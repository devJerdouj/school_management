package com.example.schoolManagement.level;

import com.example.schoolManagement.groupe.Group;
import com.example.schoolManagement.groupe.GroupResponse;
import jakarta.persistence.OneToMany;

import java.util.Set;

public record LevelResponse(
        Long id,
        String name,
        double totalCost,
        Set<GroupResponse> groups
) {
}
