package az.travellab.ms_travel_application.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientDto {
    private String name;
    private String phone;
    private String pin;
}
