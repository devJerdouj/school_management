package com.example.schoolManagement.level;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    // 1. Créer un nouveau niveau
    @PostMapping
    public ResponseEntity<Long> createLevel(@RequestBody @Valid LevelRequest levelRequest) {
        Long levelId = levelService.createLevel(levelRequest);
        return ResponseEntity.ok(levelId);
    }

    // 2. Trouver un niveau par son ID
    @GetMapping("/{levelId}")
    public ResponseEntity<LevelResponse> findById(@PathVariable Long levelId) {
        LevelResponse levelResponse = levelService.findById(levelId);
        return ResponseEntity.ok(levelResponse);
    }

    // 3. Mettre à jour un niveau
    @PutMapping("/{levelId}")
    public ResponseEntity<LevelResponse> updateLevel(
            @PathVariable Long levelId,
            @RequestBody @Valid LevelRequest levelRequest) {
        LevelResponse updatedLevel = levelService.updateLevel(levelId, levelRequest);
        return ResponseEntity.ok(updatedLevel);
    }

    // 4. Supprimer un niveau
    @DeleteMapping("/{levelId}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long levelId) {
        levelService.deleteLevel(levelId);
        return ResponseEntity.noContent().build();
    }

    // 5. Obtenir tous les niveaux
    @GetMapping
    public ResponseEntity<List<LevelResponse>> findAll() {
        List<LevelResponse> levels = levelService.findAll();
        return ResponseEntity.ok(levels);
    }

    // 6. Calculer le montant dû pour un étudiant en fonction de son niveau
    @GetMapping("/amount/student/{studentId}")
    public ResponseEntity<Double> getAmountByStudentLevel(@PathVariable Long studentId) {
        double amount = levelService.getAmountByStudentLevel(studentId);
        return ResponseEntity.ok(amount);
    }
}
