package az.travellab.ms_travel_application.model.dto;

import az.travellab.ms_travel_application.model.enums.Employee;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientCriteriaDto {
    private Long id;
    private String nameFrom;
    private String nameTo;
    private String phoneFrom;
    private String phoneTo;
    private String message;
    private String pin;
    private String mail;
    private String citizenCountry;
    private Employee username;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
}
