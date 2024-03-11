package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import az.travellab.ms_travel_application.dao.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

