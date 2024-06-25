package az.travellab.ms_travel_application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfferCriteriaDto {
    private Long offerId;
    private Long clientId;
}
