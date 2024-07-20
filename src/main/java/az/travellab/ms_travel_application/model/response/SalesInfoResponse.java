package az.travellab.ms_travel_application.model.response;

import az.travellab.ms_travel_application.model.dto.ClientDto;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import az.travellab.ms_travel_application.model.enums.ClientClass;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesInfoResponse {
    private String number;
    private Boolean isOfficial;
    private ServiceType type;
    @JsonProperty("class")
    private ClientClass clientClass;
    private String salesperson;
    private Boolean hasClientRelationship;
    private BigDecimal purchasedAmount;
    private BigDecimal soldAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripEndDate;
    private Boolean isEmployeeBonusPaid;
    private BigDecimal employeeBonus;
    private BigDecimal profit;
    private SalesStatus status;
    private String cancelReason;
    private String note;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private ClientDto clientDto;
    private List<CountryDto> countries;
    private List<SalesComponentsDto> components;
    private List<SalesPaymentsDto> payments;
}
