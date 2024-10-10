package com.example.schoolManagement.groupe;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // 1. Créer un nouveau groupe
    @PostMapping
    public ResponseEntity<Long> createGroup(@RequestBody @Valid GroupRequest groupRequest) {
        Long groupId = groupService.createGroup(groupRequest);
        return ResponseEntity.ok(groupId);
    }

    // 2. Trouver un groupe par ID
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> findById(@PathVariable Long groupId) {
        GroupResponse groupResponse = groupService.findById(groupId);
        return ResponseEntity.ok(groupResponse);
    }

    // 3. Mettre à jour un groupe
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable Long groupId,
            @RequestBody @Valid GroupRequest groupRequest) {
        GroupResponse updatedGroup = groupService.updateGroup(groupId, groupRequest);
        return ResponseEntity.ok(updatedGroup);
    }

    // 4. Supprimer un groupe
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    // 5. Obtenir tous les groupes
    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll() {
        List<GroupResponse> groups = groupService.findAll();
        return ResponseEntity.ok(groups);
    }
}
