package az.travellab.ms_travel_application.dao.entity;

import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.enums.GenderType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nameFrom;

    private String nameTo;

    private String phoneFrom;

    private String phoneTo;

    private String message;

    private String pin;

    private String mail;

    private Boolean isMarried;

    private Boolean isParent;

    @Enumerated(STRING)
    private Employee username;

    @Enumerated(STRING)
    private GenderType genderType;

    private String citizenCountry;

    private LocalDate birthDate;

    @OneToMany(cascade = {PERSIST, MERGE}, mappedBy = "client", fetch = FetchType.LAZY)
    private List<OfferEntity> offerEntities;

//    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
//    private List<SalesEntity> salesEntities;

    private LocalDateTime messageSentAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
