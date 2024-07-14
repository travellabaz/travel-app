package az.travellab.ms_travel_application.model.response;

import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import az.travellab.ms_travel_application.model.enums.ClientType;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesInfoResponse {
    private Long id;
    private String number;
    private Boolean isOfficial;
    private ServiceType type;
    private ClientType clientClass;
    private String clientName;
    private String clientPhoneNumber;
    private String clientPin;
    private String salesperson;
    private BigDecimal purchasedAmount;
    private BigDecimal soldAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripEndDate;
    private BigDecimal employeeBonus;
    private BigDecimal profit;
    private SalesStatus status;
    private String cancelReason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private List<CountryDto> countries;
    private List<SalesComponentsDto> components = new ArrayList<>();
    private List<SalesPaymentsDto> payments = new ArrayList<>();
}
