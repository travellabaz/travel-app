package az.travellab.ms_travel_application.dao.entity;

import az.travellab.ms_travel_application.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table(name = "sales_payments")
public class SalesPaymentsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sales_id")
    private SalesEntity sales;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private BigDecimal change;
    private BigDecimal remaining;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesPaymentsEntity that = (SalesPaymentsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}