package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import az.travellab.ms_travel_application.model.dto.CityDto;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.response.CountryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CountryMapper {
    COUNTRY_MAPPER;

    public List<CountryResponse> generateCountriesResponse(List<CountryEntity> countryEntities) {
        return countryEntities.stream()
                .map(country -> CountryResponse.builder()
                        .id(country.getId())
                        .name(country.getName())
                        .build())
                .toList();
    }

    public List<CountryDto> generateCountriesDto(List<CountryEntity> countryEntities, List<CityEntity> cityEntities) {
        countryEntities.addAll(cityEntities.stream()
                .map(CityEntity::getCountry)
                .toList());

        return countryEntities.stream()
                .map(CountryEntity::getName)
                .distinct()
                .map(countryName -> {
                    var cityDtos = cityEntities.stream()
                            .filter(cityEntity -> cityEntity.getCountry().getName().equals(countryName))
                            .map(cityEntity -> CityDto.builder()
                                    .id(cityEntity.getId())
                                    .name(cityEntity.getName())
                                    .build()
                            )
                            .toList();
                    return CountryDto.builder()
                            .name(countryName)
                            .cities(cityDtos)
                            .build();
                }).toList();
    }

    public List<CountryDto> createCountriesDto(List<CityEntity> cityEntities) {
        Map<String, CountryDto> countryMap = new HashMap<>();

        for (var city : cityEntities) {
            var cityDto = CityDto.builder()
                    .id(city.getId())
                    .name(city.getName())
                    .build();

            var countryName = city.getCountry().getName();
            var countryDto = countryMap.computeIfAbsent(countryName,
                    name -> CountryDto.builder()
                            .name(name)
                            .cities(new ArrayList<>())
                            .build());

            countryDto.getCities().add(cityDto);
        }
        return new ArrayList<>(countryMap.values());
    }
}
