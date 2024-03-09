package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.repository.CityRepository;
import az.travellab.ms_travel_application.factory.CityMapper;
import az.travellab.ms_travel_application.factory.CountryMapper;
import az.travellab.ms_travel_application.model.dto.CityDto;
import az.travellab.ms_travel_application.model.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.travellab.ms_travel_application.factory.CityMapper.*;
import static az.travellab.ms_travel_application.util.CountryUtil.COUNTRY_UTIL;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<CityEntity> getCitiesByMessage(String message) {
        return getCity(message, cityRepository.findAll());
    }

    private List<CityEntity> getCity(String message, List<CityEntity> cityEntities) {
        return cityEntities.stream()
                .filter(cityEntity -> COUNTRY_UTIL.findCountry(message, cityEntity.getName()))
                .toList();
    }

    public List<CityResponse> getCities() {
        return CITY_MAPPER.generateCitiesResponse(cityRepository.findAll());
    }

    public List<CityEntity> getCitiesEntity(List<Long> ids) {
        return cityRepository.findByIdIn(ids).get();
    }
}

