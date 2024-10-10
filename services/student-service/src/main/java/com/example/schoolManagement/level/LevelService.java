package com.example.schoolManagement.level;

import com.example.schoolManagement.groupe.GroupMapper;
import com.example.schoolManagement.student.Student;
import com.example.schoolManagement.student.StudentResponse;
import com.example.schoolManagement.student.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;
    private final LevelMapper levelMapper;
    private final GroupMapper groupMapper;
    private LevelService levelService;
    private StudentService studentService;



    @Transactional
    public Long createLevel(LevelRequest levelRequest) {

        Level level = levelMapper.toLevel(levelRequest);

        // Vérification : si des groupes sont fournis dans LevelRequest
        if (levelRequest.groups() != null && !levelRequest.groups().isEmpty()) {
            level.setGroups(groupMapper.toGroupSet(levelRequest.groups())); // Mapper les groupes si présents
        }


        return levelRepository.save(level).getId();
    }


    public LevelResponse findById(Long levelId) {
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new EntityNotFoundException("Level not found with ID: " + levelId));
        return levelMapper.toLevelResponse(level);
    }


    @Transactional
    public LevelResponse updateLevel(Long levelId, LevelRequest levelRequest) {

        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new EntityNotFoundException("Level not found with ID: " + levelId));


        level.setName(levelRequest.name());
        level.setTotalCost(levelRequest.totalCost());

        // Vérification : si des groupes sont fournis dans LevelRequest
        if (levelRequest.groups() != null && !levelRequest.groups().isEmpty()) {
            level.setGroups(groupMapper.toGroupSet(levelRequest.groups())); // Mapper et définir les groupes
        }

        levelRepository.save(level);
        return levelMapper.toLevelResponse(level);
    }


    @Transactional
    public boolean deleteLevel(Long levelId) {
        if (levelRepository.existsById(levelId)) {
            levelRepository.deleteById(levelId);
            return true;
        } else {
            throw new EntityNotFoundException("Level not found with ID: " + levelId);
        }
    }

    public List<LevelResponse> findAll() {
        return levelRepository.findAll().stream()
                .map(levelMapper::toLevelResponse)
                .collect(Collectors.toList());
    }

    public double getAmountByStudentLevel(Long studentId) {

        StudentResponse studentResponse = studentService.findById(studentId);

        LevelResponse levelResponse= studentResponse.level();

        return levelResponse.totalCost();
    }


}
