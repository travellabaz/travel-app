package az.travellab.ms_travel_application.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class EmployeesBonusResponse {
    private String salesperson;
    private BigDecimal bonus;
}
