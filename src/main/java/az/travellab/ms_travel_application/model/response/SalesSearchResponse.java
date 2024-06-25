package az.travellab.ms_travel_application.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SalesSearchResponse {

    private String id;
    private String number;
    private String type;
    private String clientClass;
    private String clientName;
    private String clientPhone;
    private String clientPin;
    private String salesperson;
    private String purchasedAmount;
    private String soldAmount;
    private String tripStartDate;
    private String tripEndDate;
    private String employeeBonus;
    private String profit;
    private String status;
    private String createdAt;
}
