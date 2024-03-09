package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import az.travellab.ms_travel_application.dao.entity.OfferEntity;
import az.travellab.ms_travel_application.model.dto.CityDto;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.response.CityResponse;
import az.travellab.ms_travel_application.model.response.OfferResponse;

import java.util.List;

import static az.travellab.ms_travel_application.model.enums.OfferStatus.INTERESTED;
import static az.travellab.ms_travel_application.model.enums.ServiceType.TOUR;

public enum CityMapper {
    CITY_MAPPER;

    public List<CityDto> generateCitiesDto(List<CityEntity> cityEntities) {
        return cityEntities.stream()
                .map(city -> CityDto.builder()
                        .name(city.getName())
                        .build())
                .toList();
    }

    public List<CityResponse> generateCitiesResponse(List<CityEntity> cityEntities) {
        return cityEntities.stream()
                .map(city -> CityResponse.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .build())
                .toList();
    }
}
