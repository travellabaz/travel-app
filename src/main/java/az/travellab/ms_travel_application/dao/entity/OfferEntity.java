package az.travellab.ms_travel_application.dao.entity;

import az.travellab.ms_travel_application.model.enums.OfferStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private ServiceType serviceType;

    @Enumerated(STRING)
    private OfferStatus status;

    @ManyToOne(fetch = LAZY)
    private ClientEntity client;

    @ManyToMany
    @JoinTable(
            name = "travel_offer_country",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<CountryEntity> countryEntityList;

    @ManyToMany
    @JoinTable(
            name = "travel_offer_city",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private List<CityEntity> cityEntityList;

    private LocalDateTime messageSentAt;

    private LocalDate plannedDate;

    private LocalDateTime tripDate;

    private LocalDate purchaseDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferEntity that = (OfferEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}