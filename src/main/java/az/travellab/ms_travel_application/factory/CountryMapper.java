package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import az.travellab.ms_travel_application.dao.entity.OfferEntity;
import az.travellab.ms_travel_application.model.dto.CityDto;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.response.OfferResponse;

import java.util.List;

import static az.travellab.ms_travel_application.factory.CityMapper.CITY_MAPPER;
import static az.travellab.ms_travel_application.model.enums.OfferStatus.INTERESTED;
import static az.travellab.ms_travel_application.model.enums.ServiceType.TOUR;

public enum CountryMapper {
    COUNTRY_MAPPER;

    public List<CountryDto> generateCountriesDto(List<CountryEntity> countryEntities, List<CityEntity> cityEntities) {
        countryEntities.addAll(cityEntities.stream().map(CityEntity::getCountry).toList());
        return countryEntities.stream()
                .map(CountryEntity::getName)
                .distinct()
                .map(countryName -> {
                    var cityDtos = cityEntities.stream()
                            .filter(cityEntity -> cityEntity.getCountry().getName().equals(countryName))
                            .map(cityEntity -> CityDto.builder().name(cityEntity.getName()).build())
                            .toList();
                    return CountryDto.builder()
                            .name(countryName)
                            .cities(cityDtos)
                            .build();
                }).toList();
    }
}
