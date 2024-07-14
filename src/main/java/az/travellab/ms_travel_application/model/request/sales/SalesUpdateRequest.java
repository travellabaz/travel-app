package az.travellab.ms_travel_application.model.request.sales;

import az.travellab.ms_travel_application.model.enums.ClientType;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesUpdateRequest {
    private Boolean isOfficial;
    private ServiceType type;
    private ClientType clientClass;
    private String clientNumber;
    private List<Long> citiesIds;
    private String salespersonNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tripEndDate;
    private BigDecimal employeeBonus;
    private Boolean isEmployeeBonusPaid;
    private BigDecimal profit;
    private SalesStatus status;
    private String cancelReason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime salesDate;
}
