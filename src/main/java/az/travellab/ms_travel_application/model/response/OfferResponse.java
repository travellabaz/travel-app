package az.travellab.ms_travel_application.model.response;

import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.enums.OfferStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OfferResponse {
    private Long id;
    private ServiceType serviceType;
    private OfferStatus status;
    private LocalDate plannedDate;
    private LocalDateTime messageSentAt;
    private LocalDateTime tripDate;
    private LocalDateTime returnDate;
    private LocalDate purchaseDate;
    private LocalDateTime initialPaymentDate;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private List<CountryDto> countries;
}
