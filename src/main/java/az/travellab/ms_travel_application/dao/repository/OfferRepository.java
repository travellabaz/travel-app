package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.OfferEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity, Long>, JpaSpecificationExecutor<OfferEntity> {
    @EntityGraph(attributePaths = "client")
    List<OfferEntity> findByClientPhoneFromAndClientId(String phoneFrom, Long clientId);

    List<OfferEntity> findDistinctByCityEntityListIdAndMessageSentAtBefore(Long cityId, LocalDateTime date, Pageable pageable);

//    @Query("SELECT DISTINCT o FROM OfferEntity o JOIN o.cityEntityList c WHERE c.id = :cityId AND o.messageSentAt < :date LIMIT 5")
//    List<OfferEntity> findByCityEntityListIdAndMessageSentAtBefore(Long cityId, LocalDateTime date);

    @EntityGraph(attributePaths = "client")
    Optional<OfferEntity> findByClientIdAndId(Long clientId, Long id);
}
