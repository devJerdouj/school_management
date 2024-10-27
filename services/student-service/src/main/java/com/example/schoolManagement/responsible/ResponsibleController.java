package com.example.schoolManagement.responsible;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/responsibles")
public class ResponsibleController {

    private final ResponsibleService responsibleService;

    public ResponsibleController(ResponsibleService responsibleService) {
        this.responsibleService = responsibleService;
    }

    // 1. Créer un nouveau responsable
    @PostMapping
    public ResponseEntity<Long> createResponsible(@RequestBody @Valid ResponsibleRequest responsibleRequest) {
        Long responsibleId = responsibleService.createResponsible(responsibleRequest);
        return ResponseEntity.ok(responsibleId);
    }

    // 2. Trouver un responsable par son ID
    @GetMapping("/{responsibleId}")
    public ResponseEntity<ResponsibleResponse> findById(@PathVariable Long responsibleId) {
        ResponsibleResponse responsibleResponse = responsibleService.findById(responsibleId);
        return ResponseEntity.ok(responsibleResponse);
    }

    // 3. Mettre à jour un responsable
    @PutMapping("/{responsibleId}")
    public ResponseEntity<ResponsibleResponse> updateResponsible(
            @PathVariable Long responsibleId,
            @RequestBody @Valid ResponsibleRequest responsibleRequest) {
        ResponsibleResponse updatedResponsible = responsibleService.updateResponsible(responsibleId, responsibleRequest);
        return ResponseEntity.ok(updatedResponsible);
    }

    // 4. Supprimer un responsable
    @DeleteMapping("/{responsibleId}")
    public ResponseEntity<Void> deleteResponsible(@PathVariable Long responsibleId) {
        responsibleService.deleteResponsible(responsibleId);
        return ResponseEntity.noContent().build();
    }

    // 5. Obtenir tous les responsables
    @GetMapping
    public ResponseEntity<List<ResponsibleResponse>> findAll() {
        List<ResponsibleResponse> responsibles = responsibleService.findAll();
        return ResponseEntity.ok(responsibles);
    }
}
