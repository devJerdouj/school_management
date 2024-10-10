package com.example.schoolManagement.responsible;

public class ResponsibleMapper {


    public Responsible toResponsible(ResponsibleRequest request) {
        return Responsible.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();
    }


    public ResponsibleResponse toResponsibleResponse(Responsible responsible) {
        return new ResponsibleResponse(
                responsible.getId(),
                responsible.getFirstName(),
                responsible.getLastName(),
                responsible.getEmail(),
                responsible.getPhoneNumber()
        );
    }
}
