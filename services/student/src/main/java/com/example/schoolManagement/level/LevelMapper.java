package com.example.schoolManagement.level;

import com.example.schoolManagement.groupe.GroupMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class LevelMapper {

    private final GroupMapper groupMapper;

    public LevelMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    public Level toLevel(LevelRequest request) {
        return Level.builder()
                .id(request.id())
                .name(request.name())
                .totalCost(request.totalCost())
                .groups(request.groups() != null
                        ? request.groups().stream().map(groupMapper::toGroup).collect(Collectors.toSet())
                        : new HashSet<>())
                .build();
    }

    public LevelResponse toLevelResponse(Level level) {
        return new LevelResponse(
                level.getId(),
                level.getName(),
                level.getTotalCost(),
                level.getGroups() != null
                        ? level.getGroups().stream().map(groupMapper::toGroupResponse).collect(Collectors.toSet())
                        : new HashSet<>());
    }



}
