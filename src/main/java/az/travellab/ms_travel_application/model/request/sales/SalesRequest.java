package az.travellab.ms_travel_application.model.request.sales;

import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import az.travellab.ms_travel_application.model.enums.ClientClass;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesRequest {
    private String number;
    private Boolean isOfficial;
    private ServiceType type;
    @JsonProperty("class")
    private ClientClass clientClass;
    private String salespersonNumber;
    private Boolean hasClientRelationship;
    private Boolean isEmployeeBonusPaid;
    private String clientNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripEndDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String cancelReason;
    private List<Long> citiesIds;
    private List<SalesComponentsDto> components;
    private List<SalesPaymentsDto> payments;
}
