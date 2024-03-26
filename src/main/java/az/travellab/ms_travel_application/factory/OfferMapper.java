package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import az.travellab.ms_travel_application.dao.entity.OfferEntity;
import az.travellab.ms_travel_application.model.request.OfferRequest;
import az.travellab.ms_travel_application.model.response.OfferResponse;

import java.util.List;

import static az.travellab.ms_travel_application.factory.CountryMapper.COUNTRY_MAPPER;
import static az.travellab.ms_travel_application.model.enums.OfferStatus.INTERESTED;
import static az.travellab.ms_travel_application.model.enums.ServiceType.TOUR;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;

public enum OfferMapper {
    OFFER_MAPPER;

    public OfferEntity generateOfferEntity(ClientEntity clientEntity, List<CountryEntity> countryEntities, List<CityEntity> cityEntities) {
        return OfferEntity.builder()
                .client(clientEntity)
                .countryEntityList(!countryEntities.isEmpty() ? countryEntities :
                        !cityEntities.isEmpty() ? cityEntities.stream().map(CityEntity::getCountry).toList() : emptyList()
                )
                .cityEntityList(cityEntities)
                .status(INTERESTED)
                .serviceType(TOUR)
                .messageSentAt(now())
                .build();
    }

    public void updateOfferEntity(OfferEntity offerEntity, OfferRequest offerUpdateRequest, List<CityEntity> cityEntities) {
        if (offerUpdateRequest.getStatus() != null) offerEntity.setStatus(offerUpdateRequest.getStatus());
        if (offerUpdateRequest.getServiceType() != null)
            offerEntity.setServiceType(offerUpdateRequest.getServiceType());
        if (offerUpdateRequest.getPlannedDate() != null)
            offerEntity.setPlannedDate(offerUpdateRequest.getPlannedDate());
        if (offerUpdateRequest.getTripDate() != null) offerEntity.setTripDate(offerUpdateRequest.getTripDate());
        if (offerUpdateRequest.getPurchaseDate() != null)
            offerEntity.setPurchaseDate(offerUpdateRequest.getPurchaseDate());
        var cityIds = offerEntity.getCityEntityList().stream().map(CityEntity::getId).toList();
        offerEntity.getCityEntityList().addAll(
                cityEntities.stream()
                        .filter(cityEntity -> !cityIds.contains(cityEntity.getId()))
                        .toList()
        );
        offerEntity.getCountryEntityList().addAll(cityEntities.stream().map(CityEntity::getCountry).toList());
    }

    public List<OfferResponse> generateOfferResponse(List<OfferEntity> offerEntities) {
        return offerEntities.stream()
                .map(offer -> OfferResponse.builder()
                        .id(offer.getId())
                        .serviceType(offer.getServiceType())
                        .status(offer.getStatus())
                        .plannedDate(offer.getPlannedDate())
                        .tripDate(offer.getTripDate())
                        .purchaseDate(offer.getPurchaseDate())
                        .createdAt(offer.getCreatedAt())
                        .messageSentAt(offer.getMessageSentAt())
                        .countries(COUNTRY_MAPPER.generateCountriesDto(offer.getCountryEntityList(), offer.getCityEntityList()))
                        .build()
                ).toList();

    }

    public OfferEntity generateOfferEntity(ClientEntity clientEntity, List<CityEntity> cityEntities, OfferRequest offerRequest) {
        return OfferEntity.builder()
                .client(clientEntity)
                .countryEntityList(cityEntities.stream().map(CityEntity::getCountry).toList())
                .cityEntityList(cityEntities)
                .status(offerRequest.getStatus())
                .serviceType(offerRequest.getServiceType())
                .messageSentAt(now())
                .plannedDate(offerRequest.getPlannedDate())
                .tripDate(offerRequest.getTripDate())
                .purchaseDate(offerRequest.getPurchaseDate())
                .build();
    }
}
