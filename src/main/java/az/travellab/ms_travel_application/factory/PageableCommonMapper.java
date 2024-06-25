package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.model.response.ClientResponse;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import az.travellab.ms_travel_application.model.response.PageableClientResponse;

import java.util.List;

public enum PageableCommonMapper {
    PAGEABLE_COMMON_MAPPER;

    public PageableClientResponse generatePageableClientResponse(List<ClientResponse> clientEntities, int totalPages, boolean hasNextPage, long totalElements) {
        return PageableClientResponse.builder()
                .clients(clientEntities)
                .hasNextPage(hasNextPage)
                .totalElements(totalElements)
                .lastPageNumber(totalPages)
                .build();
    }

    public <T> CommonPageableResponse<T> buildPageableClientResponse(List<T> data, Long count) {
        return CommonPageableResponse.<T>builder()
                .data(data)
                .totalElements(count)
                .build();
    }
}
