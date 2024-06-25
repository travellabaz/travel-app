package az.travellab.ms_travel_application.model.request.sales;

import az.travellab.ms_travel_application.model.enums.ClientType;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesRequest {
    private Boolean isOfficial;
    private ServiceType type;
    @JsonProperty("class")
    private ClientType clientClass;
    private String salespersonNumber;
    private String clientNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripEndDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime salesDate;
    private List<Long> citiesIds;
    private List<SalesComponentsRequest> components;
    private List<SalesPaymentsRequest> payments;
}
