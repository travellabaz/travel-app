package az.travellab.ms_travel_application.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CountryDto {
    private String name;
    private List<CityDto> cities;
}
