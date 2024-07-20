package az.travellab.ms_travel_application.model.response;

import az.travellab.ms_travel_application.model.enums.ClientClass;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SalesSearchResponse {
    private String number;
    private Boolean isOfficial;
    private ServiceType type;
    private ClientClass clientClass;
    private String clientName;
    private String clientPhone;
    private String clientPin;
    private String salesperson;
    private Boolean hasClientRelationship;
    private BigDecimal purchasedAmount;
    private BigDecimal soldAmount;
    private LocalDateTime tripStartDate;
    private LocalDateTime tripEndDate;
    private BigDecimal employeeBonus;
    private Boolean isEmployeeBonusPaid;
    private BigDecimal profit;
    private SalesStatus status;
    private String cancelReason;
    private String note;
    private LocalDateTime createdAt;
}
