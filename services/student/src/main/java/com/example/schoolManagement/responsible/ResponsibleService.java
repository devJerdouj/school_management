package com.example.schoolManagement.responsible;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class ResponsibleService {

    private final ResponsibleRepository responsibleRepository;
    private final ResponsibleMapper responsibleMapper;

    public ResponsibleService(ResponsibleRepository responsibleRepository, ResponsibleMapper responsibleMapper) {
        this.responsibleRepository = responsibleRepository;
        this.responsibleMapper = responsibleMapper;
    }

    @Transactional
    public Long createResponsible(ResponsibleRequest responsibleRequest) {
        Responsible responsible = responsibleMapper.toResponsible(responsibleRequest);
        return responsibleRepository.save(responsible).getId();
    }

    public ResponsibleResponse findById(Long responsibleId) {
        Responsible responsible = responsibleRepository.findById(responsibleId)
                .orElseThrow(() -> new EntityNotFoundException("Responsible not found with ID: " + responsibleId));
        return responsibleMapper.toResponsibleResponse(responsible);
    }

    @Transactional
    public ResponsibleResponse updateResponsible(Long responsibleId, ResponsibleRequest responsibleRequest) {
        Responsible responsible = responsibleRepository.findById(responsibleId)
                .orElseThrow(() -> new EntityNotFoundException("Responsible not found with ID: " + responsibleId));

        responsible.setFirstName(responsibleRequest.firstName());
        responsible.setLastName(responsibleRequest.lastName());
        responsible.setEmail(responsibleRequest.email());  // Ajout de l'email
        responsible.setPhoneNumber(responsibleRequest.phoneNumber());

        responsibleRepository.save(responsible);
        return responsibleMapper.toResponsibleResponse(responsible);
    }

    @Transactional
    public boolean deleteResponsible(Long responsibleId) {
        if (responsibleRepository.existsById(responsibleId)) {
            responsibleRepository.deleteById(responsibleId);
            return true;
        } else {
            throw new EntityNotFoundException("Responsible not found with ID: " + responsibleId);
        }
    }

    public List<ResponsibleResponse> findAll() {
        return responsibleRepository.findAll().stream()
                .map(responsibleMapper::toResponsibleResponse)
                .collect(Collectors.toList());
    }
}
