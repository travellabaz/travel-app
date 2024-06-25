package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.repository.ClientRepository;
import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import az.travellab.ms_travel_application.model.enums.FilterType;
import az.travellab.ms_travel_application.model.request.ClientRegistrationRequest;
import az.travellab.ms_travel_application.model.request.ClientUpdateRequest;
import az.travellab.ms_travel_application.model.response.ClientResponse;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static az.travellab.ms_travel_application.factory.ClientMapper.CLIENT_MAPPER;
import static az.travellab.ms_travel_application.factory.OfferMapper.OFFER_MAPPER;
import static az.travellab.ms_travel_application.factory.PageableCommonMapper.PAGEABLE_COMMON_MAPPER;
import static az.travellab.ms_travel_application.model.enums.PaymentMessageQueries.GET_ALL_CLIENTS;
import static az.travellab.ms_travel_application.model.enums.PaymentMessageQueries.GET_ALL_CLIENTS_COUNT;
import static az.travellab.ms_travel_application.util.HttpContextUtil.HTTP_CONTEXT_UTIL;
import static az.travellab.ms_travel_application.util.PageUtil.PAGE_UTIL;
import static az.travellab.ms_travel_application.util.PaymentResponseTransformerUtil.PAYMENT_RESPONSE_TRANSFORMER;
import static az.travellab.ms_travel_application.util.QueryGeneratorUtil.buildBaseQuery;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.springframework.data.domain.Pageable.unpaged;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final CityService cityService;
    private final CountryService countryService;
    private final OfferRepository offerRepository;
    private final ClientRepository clientRepository;
    @PersistenceContext
    private final EntityManager entityManager;

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

    public Optional<ClientEntity> prepareFindClientEntityByPhoneFrom(String phone) {
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
        var client = clientRepository.findById(clientUpdateRequest.getId()).get();
        CLIENT_MAPPER.updateClientEntity(clientUpdateRequest, client);
        prepareSaveOrUpdateClientEntity(client);
    }

    public ClientResponse getClientInfo(String phone) {
        return CLIENT_MAPPER.generateClientResponse(
                prepareFindClientEntityByPhoneFrom(phone).get()
        );
    }

    public CommonPageableResponse<ClientResponse> getClients() {
        var nameValueParams = HTTP_CONTEXT_UTIL.getNameValueParams();
        var pageRequest = PAGE_UTIL.getPageRequest();
        var paymentResponseList = supplyAsync(() -> getClientList(nameValueParams, pageRequest));
        var paymentResponseListCount = supplyAsync(() -> getPaymentListCount(nameValueParams));
        return PAGEABLE_COMMON_MAPPER.buildPageableClientResponse(paymentResponseList.join(), paymentResponseListCount.join());
    }

    private List<ClientResponse> getClientList(Map<String, String> nameValueParams, Pageable pageable) {
        return buildBaseQuery(GET_ALL_CLIENTS.getBaseQuery())
                .generateFilter(nameValueParams.keySet(), FilterType.CLIENTS)
                .endWith(GET_ALL_CLIENTS.getEndOfQuery())
                .getTypedQuery(entityManager, nameValueParams, pageable)
                .unwrap(Query.class)
                .setTupleTransformer(PAYMENT_RESPONSE_TRANSFORMER)
                .getResultList();
    }

    private Long getPaymentListCount(Map<String, String> nameValueParams) {
        return (Long) buildBaseQuery(GET_ALL_CLIENTS_COUNT.getBaseQuery())
                .generateFilter(nameValueParams.keySet(), FilterType.CLIENTS)
                .getTypedQuery(entityManager, nameValueParams, unpaged())
                .getSingleResult();
    }
}