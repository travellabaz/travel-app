package az.travellab.ms_travel_application.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryResponse {
    private Long id;
    private String name;
}
