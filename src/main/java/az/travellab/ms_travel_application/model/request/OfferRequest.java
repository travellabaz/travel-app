package az.travellab.ms_travel_application.model.request;

import az.travellab.ms_travel_application.model.enums.OfferStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfferRequest {
    private Long id;
    private Long clientId;
    private ServiceType serviceType;
    private OfferStatus status;
    private LocalDate plannedDate;
    private LocalDateTime tripDate;
    private LocalDateTime returnDate;
    private LocalDate purchaseDate;
    private LocalDateTime paymentDate;
    private LocalDateTime initialPaymentDate;
    private List<Long> citiesIds;
}
