package az.travellab.ms_travel_application.model.request.sales;

import az.travellab.ms_travel_application.model.enums.SalesComponentsStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesComponentsRequest {
    private Long id;
    private ServiceType type;
    private String name;
    private String bookingNumber;
    private BigDecimal percentage;
    private String description;
    private BigDecimal purchasedAmount;
    private BigDecimal soldAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainedAmount;
    private BigDecimal transferCommission;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private SalesComponentsStatus status;
}
