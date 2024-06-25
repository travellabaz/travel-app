package az.travellab.ms_travel_application.model.request;

import lombok.Data;

@Data
public class ClientRegistrationRequest {
    private String name;
    private String phoneTo;
    private String phoneFrom;
    private String message;
}
