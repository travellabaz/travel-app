package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.response.CityResponse;
import az.travellab.ms_travel_application.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static az.travellab.ms_travel_application.factory.CountryMapper.COUNTRY_MAPPER;

@Api(path = "v1")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("city/all")
    public List<CityResponse> getCities() {
        return cityService.getCities();
    }

    @GetMapping("location/all")
    public List<CountryDto> getLocations() {
        return COUNTRY_MAPPER.createCountriesDto(cityService.getCitiesEntity());
    }
}
