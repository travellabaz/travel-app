package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import az.travellab.ms_travel_application.dao.repository.CountryRepository;
import az.travellab.ms_travel_application.factory.CountryMapper;
import az.travellab.ms_travel_application.model.response.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.travellab.ms_travel_application.factory.CityMapper.CITY_MAPPER;
import static az.travellab.ms_travel_application.factory.CountryMapper.COUNTRY_MAPPER;
import static az.travellab.ms_travel_application.util.CountryUtil.COUNTRY_UTIL;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CityService cityService;
    private final CountryRepository countryRepository;

    public List<CountryEntity> getCountriesByMessage(String message) {
        return getCounties(message, countryRepository.findAll());
    }

    private List<CountryEntity> getCounties(String message, List<CountryEntity> countryEntities) {
        return countryEntities.stream()
                .filter(countryEntity -> COUNTRY_UTIL.findCountry(message, countryEntity.getName()))
                .toList();
    }

    public List<CountryResponse> getCountries() {
        return COUNTRY_MAPPER.generateCountriesResponse(countryRepository.findAll());
    }
}

