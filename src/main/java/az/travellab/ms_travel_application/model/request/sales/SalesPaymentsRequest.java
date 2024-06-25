package az.travellab.ms_travel_application.model.request.sales;

import az.travellab.ms_travel_application.model.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesPaymentsRequest {
    private Long id;
    private BigDecimal amount;
    private BigDecimal remaining;
    private PaymentType type;
    private BigDecimal change;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
