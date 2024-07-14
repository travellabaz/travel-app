package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.model.response.CityResponse;

import java.util.List;

public enum CityMapper {
    CITY_MAPPER;
    public List<CityResponse> generateCitiesResponse(List<CityEntity> cityEntities) {
        return cityEntities.stream()
                .map(city -> CityResponse.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .build())
                .toList();
    }
}
