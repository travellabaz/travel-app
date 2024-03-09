package az.travellab.ms_travel_application.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientUpdateRequest {
    private String nameFrom;
    private String phoneFrom;
    private String pin;
    private String mail;
    private String citizenCountry;
    private LocalDate birthDate;
}
