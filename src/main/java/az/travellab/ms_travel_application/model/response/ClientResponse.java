package az.travellab.ms_travel_application.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClientResponse {
    private String id;
    private String nameTo;
    private String phoneTo;
    private String nameFrom;
    private String phoneFrom;
    private String message;
    private String pin;
    private String mail;
    private String isMarried;
    private String isParent;
    private String username;
    private String genderType;
    private String citizenCountry;
    private String birthDate;
    private String createdAt;
    private String messageSentAt;
}
