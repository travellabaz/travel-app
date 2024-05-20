package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.model.dto.ClientCriteriaDto;
import az.travellab.ms_travel_application.model.enums.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public enum ClientCriteriaMapper {
    CLIENT_CRITERIA_MAPPER;

    public ClientCriteriaDto generateClientCriteriaDto(Map<String, String> map) {
        var clientCriteriaDto = new ClientCriteriaDto();
        if (map.containsKey("id")) {
            clientCriteriaDto.setId(Long.parseLong(map.get("id")));
        }
        clientCriteriaDto.setNameFrom(map.get("nameFrom"));
        clientCriteriaDto.setNameTo(map.get("nameTo"));
        clientCriteriaDto.setPhoneFrom(map.get("phoneFrom"));
        clientCriteriaDto.setPhoneTo(map.get("phoneTo"));
        clientCriteriaDto.setMessage(map.get("message"));
        clientCriteriaDto.setPin(map.get("pin"));
        clientCriteriaDto.setMail(map.get("mail"));
        clientCriteriaDto.setCitizenCountry(map.get("citizenCountry"));
        if (map.containsKey("username")) {
            clientCriteriaDto.setUsername(Employee.valueOf(map.get("username")));
        }
        if (map.containsKey("birthDate")) {
            clientCriteriaDto.setBirthDate(LocalDate.parse(map.get("birthDate")));
        }
        if (map.containsKey("createdAt")) {
            clientCriteriaDto.setCreatedAt(LocalDateTime.parse(map.get("createdAt")));
        }
        return clientCriteriaDto;
    }
}
