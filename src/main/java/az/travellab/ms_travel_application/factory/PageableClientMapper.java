package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.request.ClientRegistrationRequest;
import az.travellab.ms_travel_application.model.request.ClientUpdateRequest;
import az.travellab.ms_travel_application.model.response.ClientResponse;
import az.travellab.ms_travel_application.model.response.PageableClientResponse;

import java.util.List;

import static az.travellab.ms_travel_application.model.constants.TravelApplicationConstants.AZE;

public enum PageableClientMapper {
    PAGEABLE_CLIENT_MAPPER;

    public PageableClientResponse generatePageableClientResponse(List<ClientResponse> clientEntities, int totalPages, boolean hasNextPage, long totalElements) {
        return PageableClientResponse.builder()
                .clients(clientEntities)
                .hasNextPage(hasNextPage)
                .totalElements(totalElements)
                .lastPageNumber(totalPages)
                .build();
    }
}
