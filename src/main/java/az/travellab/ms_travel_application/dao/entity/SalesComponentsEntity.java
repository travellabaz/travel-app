package az.travellab.ms_travel_application.dao.entity;

import az.travellab.ms_travel_application.model.enums.SalesComponentsStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table(name = "sales_components")
public class SalesComponentsEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sales_id")
    private SalesEntity sales;
    @Enumerated(STRING)
    private ServiceType type;
    private String name;
    private String bookingNumber;
    private BigDecimal percentage;
    private String description;
    private BigDecimal purchasedAmount;
    private BigDecimal soldAmount;
    private BigDecimal paidAmount;
    private BigDecimal transferCommission = new BigDecimal(0);
    private BigDecimal remainedAmount = new BigDecimal(0);
    private Boolean isInvoiceSent;
    @Enumerated(STRING)
    private SalesComponentsStatus status;
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesComponentsEntity that = (SalesComponentsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}