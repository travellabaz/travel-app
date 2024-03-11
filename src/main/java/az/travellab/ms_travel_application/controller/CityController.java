package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.response.CityResponse;
import az.travellab.ms_travel_application.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Api(path = "v1/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/all")
    public List<CityResponse> getCities() {
        return cityService.getCities();
    }
}
