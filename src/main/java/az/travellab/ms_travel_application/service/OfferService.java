package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.repository.ClientRepository;
import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import az.travellab.ms_travel_application.exception.NotFoundException;
import az.travellab.ms_travel_application.model.request.OfferRequest;
import az.travellab.ms_travel_application.model.response.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.travellab.ms_travel_application.exception.ExceptionMessages.NOT_FOUND_OFFER;
import static az.travellab.ms_travel_application.factory.OfferMapper.OFFER_MAPPER;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final CityService cityService;
    private final OfferRepository offerRepository;
    private final ClientRepository clientRepository;

    public List<OfferResponse> getOfferInfo(Long clientId, String phone) {
        var offerEntities = offerRepository.findByClientPhoneFromAndClientId(phone, clientId);
        return OFFER_MAPPER.generateOfferResponse(offerEntities);
    }

    public void offerInfoUpdate(OfferRequest offerRequest) {
        var offerEntity = offerRepository.findByClientIdAndId(offerRequest.getClientId(), offerRequest.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_OFFER.getMessage().formatted(offerRequest.getId())));

        var cityEntities = cityService.getCitiesEntity(offerRequest.getCitiesIds());
        OFFER_MAPPER.updateOfferEntity(offerEntity, offerRequest, cityEntities);
        offerRepository.save(offerEntity);
    }
    public void createOffer(OfferRequest offerRequest) {
        var clientEntity = clientRepository.findById(offerRequest.getClientId()).get();
        var citiesEntity = cityService.getCitiesEntity(offerRequest.getCitiesIds());
        var offerEntity = OFFER_MAPPER.generateOfferEntity(
                clientEntity,
                citiesEntity,
                offerRequest
        );
        offerRepository.save(offerEntity);
    }
}
