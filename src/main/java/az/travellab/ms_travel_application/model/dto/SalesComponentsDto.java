package az.travellab.ms_travel_application.model.dto;

import az.travellab.ms_travel_application.model.enums.SalesComponentsStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesComponentsDto {
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
    private SalesComponentsStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
