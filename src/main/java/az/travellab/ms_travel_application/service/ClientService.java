package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.annotation.Log;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.repository.ClientRepository;
import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import az.travellab.ms_travel_application.factory.ClientCriteriaMapper;
import az.travellab.ms_travel_application.model.dto.ClientCriteriaDto;
import az.travellab.ms_travel_application.model.response.ClientResponse;
import az.travellab.ms_travel_application.model.request.ClientRegistrationRequest;
import az.travellab.ms_travel_application.model.request.ClientUpdateRequest;
import az.travellab.ms_travel_application.model.response.PageableClientResponse;
import az.travellab.ms_travel_application.service.specification.ClientSpecification;
import az.travellab.ms_travel_application.util.HttpContextUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static az.travellab.ms_travel_application.factory.ClientCriteriaMapper.*;
import static az.travellab.ms_travel_application.factory.ClientMapper.CLIENT_MAPPER;
import static az.travellab.ms_travel_application.factory.OfferMapper.OFFER_MAPPER;
import static az.travellab.ms_travel_application.factory.PageableClientMapper.*;
import static az.travellab.ms_travel_application.util.HttpContextUtil.*;
import static az.travellab.ms_travel_application.util.PageUtil.PAGE_UTIL;

@Log
@Service
@RequiredArgsConstructor
public class ClientService {

    private final CityService cityService;
    private final CountryService countryService;
    private final OfferRepository offerRepository;
    private final ClientRepository clientRepository;

    public void clientRegistration(ClientRegistrationRequest clientRegistrationRequest) {
        var client = prepareFindClientEntityByPhoneFrom(clientRegistrationRequest.getPhoneFrom());
        if (client.isEmpty()) {
            var clientEntity = prepareSaveOrUpdateClientEntity(
                    CLIENT_MAPPER.generateClientEntity(clientRegistrationRequest)
            );
            prepareSaveOffer(clientRegistrationRequest.getMessage(), clientEntity);
        } else {
            prepareSaveOffer(clientRegistrationRequest.getMessage(), client.get());
        }
    }

    private ClientEntity prepareSaveOrUpdateClientEntity(ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    private Optional<ClientEntity> prepareFindClientEntityByPhoneFrom(String phone) {
        return clientRepository.findClientEntityByPhoneFrom(phone);
    }

    private void prepareSaveOffer(String message, ClientEntity clientEntity) {
        var offerEntity = OFFER_MAPPER.generateOfferEntity(
                clientEntity,
                countryService.getCountriesByMessage(message),
                cityService.getCitiesByMessage(message)
        );
        offerRepository.save(offerEntity);
    }

    public void clientInfoUpdate(ClientUpdateRequest clientUpdateRequest) {
        var client = prepareFindClientEntityByPhoneFrom(clientUpdateRequest.getPhoneFrom()).get();
        CLIENT_MAPPER.updateClientEntity(clientUpdateRequest, client);
        prepareSaveOrUpdateClientEntity(client);
    }

    public ClientResponse getClientInfo(String phone) {
        return CLIENT_MAPPER.generateClientResponse(
                prepareFindClientEntityByPhoneFrom(phone).get()
        );
    }

    public PageableClientResponse getClients() {
        var nameValueParams = HTTP_CONTEXT_UTIL.getNameValueParams();
        var pageRequest = PAGE_UTIL.getPageRequest();

        var clientCriteriaDto = CLIENT_CRITERIA_MAPPER.generateClientCriteriaDto(nameValueParams);
        var clientPage = clientRepository.findAll(new ClientSpecification(clientCriteriaDto), pageRequest);
        var clients = clientPage.getContent().stream().map(CLIENT_MAPPER::generateClientResponse).toList();

        return PAGEABLE_CLIENT_MAPPER.generatePageableClientResponse(clients, clientPage.getTotalPages(), clientPage.hasNext(), clientPage.getTotalElements());
    }
}