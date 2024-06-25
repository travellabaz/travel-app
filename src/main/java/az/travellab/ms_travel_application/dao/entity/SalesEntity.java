package az.travellab.ms_travel_application.dao.entity;

import az.travellab.ms_travel_application.model.enums.ClientType;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "sales")
public class SalesEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String number;
    private Boolean isOfficial;
    @Enumerated(STRING)
    private ServiceType type;
    @Enumerated(STRING)
    @Column(name = "CLASS")
    private ClientType clientClass;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;
    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "sales_city",
            joinColumns = @JoinColumn(name = "sales_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private List<CityEntity> cities;
    private String salesperson;
    private BigDecimal purchasedAmount;
    private BigDecimal soldAmount;
    private LocalDateTime tripStartDate;
    private LocalDateTime tripEndDate;
    private BigDecimal employeeBonus;
    private Boolean isEmployeeBonusPaid;
    private BigDecimal profit;
    @Enumerated(STRING)
    private SalesStatus status;
    private String cancelReason;
    @OneToMany(cascade = {PERSIST, MERGE}, mappedBy = "sales", fetch = LAZY)
    private List<SalesComponentsEntity> components;
    @OneToMany(cascade = {PERSIST, MERGE}, mappedBy = "sales", fetch = LAZY)
    private List<SalesPaymentsEntity> payments;
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesEntity that = (SalesEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}