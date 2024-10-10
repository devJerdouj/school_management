package com.example.schoolManagement.groupe;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupMapper {

    public Group toGroup(GroupRequest request) {
        return Group.builder()
                .id(request.id())
                .name(request.name())
                .groupCapacity(request.groupCapacity())
                .studentsNumber(request.studentsNumber())
                .build();
    }

    public GroupResponse toGroupResponse(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getStudentsNumber(),
                group.getGroupCapacity()
        );
    }

    public Set<GroupResponse> toGroupResponseSet(Set<Group> groups) {
        return groups.stream()
                .map(this::toGroupResponse)
                .collect(Collectors.toSet());
    }

    public Set<Group> toGroupSet(Set<GroupRequest> groupRequests) {
        return groupRequests.stream()
                .map(this::toGroup)  // Utilise la méthode toGroup pour chaque élément
                .collect(Collectors.toSet());  // Collecte en un Set
    }
}
