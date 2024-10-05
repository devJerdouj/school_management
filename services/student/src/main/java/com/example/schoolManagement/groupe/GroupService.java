package com.example.schoolManagement.groupe;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }


    @Transactional
    public Long createGroup(GroupRequest groupRequest) {
        Group group = groupMapper.toGroup(groupRequest);
        return groupRepository.save(group).getId();
    }


    public GroupResponse findById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with ID: " + groupId));
        return groupMapper.toGroupResponse(group);
    }


    @Transactional
    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with ID: " + groupId));  // Gestion de l'erreur si le groupe n'est pas trouvé


        group.setName(groupRequest.name());
        group.setGroupCapacity(groupRequest.groupCapacity());
        group.setStudentsNumber(groupRequest.studentsNumber());

        groupRepository.save(group);
        return groupMapper.toGroupResponse(group);
    }


    @Transactional
    public boolean deleteGroup(Long groupId) {
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
            return true;
        } else {
            throw new EntityNotFoundException("Group not found with ID: " + groupId);  // Lève une exception si le groupe n'est pas trouvé
        }
    }


    public List<GroupResponse> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toGroupResponse)
                .collect(Collectors.toList());
    }
}
