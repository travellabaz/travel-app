package az.travellab.ms_travel_application.model.request;

import az.travellab.ms_travel_application.model.enums.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientUpdateRequest {
    private Long id;
    private String nameFrom;
    private String phoneFrom;
    private String pin;
    private String mail;
    private GenderType genderType;
    private Boolean isMarried;
    private Boolean isParent;
    private String citizenCountry;
    private LocalDate birthDate;
}
