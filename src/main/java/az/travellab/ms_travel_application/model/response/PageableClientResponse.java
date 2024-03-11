package az.travellab.ms_travel_application.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageableClientResponse {
    private List<ClientResponse> clients;
    private int lastPageNumber;
    private long totalElements;
    private boolean hasNextPage;
}
