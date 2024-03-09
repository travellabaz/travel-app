package az.travellab.ms_travel_application.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ClientResponse {
    private Long id;
    private String nameFrom;
    private String nameTo;
    private String phoneFrom;
    private String phoneTo;
    private String message;
    private String pin;
    private String mail;
    private String citizenCountry;
    private LocalDate birthDate;
    private LocalDateTime messageSentAt;
    private LocalDateTime createdAt;
}
