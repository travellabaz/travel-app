package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.model.request.ClientRegistrationRequest;
import az.travellab.ms_travel_application.model.request.ClientUpdateRequest;
import az.travellab.ms_travel_application.model.response.ClientResponse;

import java.time.LocalDate;

import static az.travellab.ms_travel_application.model.constants.TravelApplicationConstants.AZE;
import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeByPhone;

public enum ClientMapper {
    CLIENT_MAPPER;

    public ClientEntity generateClientEntity(ClientRegistrationRequest clientRegistrationRequest) {
        var employee = getEmployeeByPhone(clientRegistrationRequest.getPhoneTo());
        return ClientEntity
                .builder()
                .message(clientRegistrationRequest.getMessage())
                .nameFrom(clientRegistrationRequest.getName())
                .phoneFrom(clientRegistrationRequest.getPhoneFrom())
                .phoneTo(employee.getPhone())
                .nameTo(employee.getName())
                .username(employee)
                .citizenCountry(AZE)
                .build();
    }

    public void updateClientEntity(ClientUpdateRequest clientUpdateRequest, ClientEntity clientEntity) {
        if (clientUpdateRequest.getBirthDate() != null) clientEntity.setBirthDate(clientUpdateRequest.getBirthDate());
        if (clientUpdateRequest.getMail() != null) clientEntity.setMail(clientUpdateRequest.getMail());
        if (clientUpdateRequest.getPhoneFrom() != null) clientEntity.setPhoneFrom(clientUpdateRequest.getPhoneFrom());
        if (clientUpdateRequest.getNameFrom() != null) clientEntity.setNameFrom(clientUpdateRequest.getNameFrom());
        if (clientUpdateRequest.getCitizenCountry() != null)
            clientEntity.setCitizenCountry(clientUpdateRequest.getCitizenCountry());
        if (clientUpdateRequest.getPin() != null) clientEntity.setPin(clientUpdateRequest.getPin());
        if (clientUpdateRequest.getGenderType() != null)
            clientEntity.setGenderType(clientUpdateRequest.getGenderType());
        if (clientUpdateRequest.getIsMarried() != null) clientEntity.setIsMarried(clientUpdateRequest.getIsMarried());
        if (clientUpdateRequest.getIsParent() != null) clientEntity.setIsParent(clientUpdateRequest.getIsParent());
    }

    public ClientResponse generateClientResponse(ClientEntity clientEntity) {
        return ClientResponse.builder()
                .id(String.valueOf(clientEntity.getId()))
                .mail(clientEntity.getMail())
                .birthDate(String.valueOf(clientEntity.getBirthDate()))
                .pin(clientEntity.getPin())
                .citizenCountry(clientEntity.getCitizenCountry())
                .message(clientEntity.getMessage())
                .nameFrom(clientEntity.getNameFrom())
                .nameTo(clientEntity.getNameTo())
                .phoneFrom(clientEntity.getPhoneFrom())
                .phoneTo(clientEntity.getPhoneTo())
                .messageSentAt(String.valueOf(clientEntity.getMessageSentAt()))
                .createdAt(String.valueOf(LocalDate.from(clientEntity.getCreatedAt())))
                .genderType(String.valueOf(clientEntity.getGenderType()))
                .isMarried(String.valueOf(clientEntity.getIsMarried()))
                .isParent(String.valueOf(clientEntity.getIsParent()))
                .build();
    }
}
