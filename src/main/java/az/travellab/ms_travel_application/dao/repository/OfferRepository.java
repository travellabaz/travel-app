package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.OfferEntity;
import az.travellab.ms_travel_application.model.enums.OfferStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity, Long>, JpaSpecificationExecutor<OfferEntity> {
    @EntityGraph(attributePaths = "client")
    List<OfferEntity> findByClientPhoneFromAndClientId(String phoneFrom, Long clientId);
    List<OfferEntity> findDistinctByCityEntityListIdAndMessageSentAtBefore(Long cityId, LocalDateTime date, Pageable pageable);
    @EntityGraph(attributePaths = "client")
    Optional<OfferEntity> findByClientIdAndId(Long clientId, Long id);
    @Query("SELECT o FROM OfferEntity o JOIN FETCH o.client WHERE o.tripDate BETWEEN :startDate AND :endDate AND o.status = :status AND o.serviceType IN :serviceTypes")
    List<OfferEntity> findByTripDateBetweenAndStatusAndServiceTypeIn(LocalDateTime startDate,LocalDateTime endDate, OfferStatus status, List<ServiceType> serviceTypes);
}
