package az.travellab.ms_travel_application.model.response;

import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.enums.OfferStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
public class OfferResponse {
    private Long id;
    private ServiceType serviceType;
    private OfferStatus status;
    private LocalDate plannedDate;
    private LocalDateTime messageSentAt;
    private LocalDateTime tripDate;
    private LocalDate purchaseDate;
    private LocalDateTime createdAt;
    private List<CountryDto> countries;
}
