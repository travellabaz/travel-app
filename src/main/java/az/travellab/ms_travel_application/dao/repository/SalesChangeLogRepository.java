package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.SalesChangeLogEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesChangeLogRepository extends CrudRepository<SalesChangeLogEntity, Long> {

    @EntityGraph(attributePaths = "sales")
    @Query("SELECT s FROM SalesChangeLogEntity s JOIN FETCH s.sales WHERE s.sales.number = :salesNumber AND s.versionId = :versionId")
    Optional<SalesChangeLogEntity> getChangeLogEntity(String salesNumber, Integer versionId);

//    @Modifying
//    @Transactional
//    @Query("UPDATE SalesChangeLogEntity s SET s.request = :request WHERE s.sales.number = :salesNumber AND s.versionId = :versionId")
//    void updateChangeLogEntity(String salesNumber, Integer versionId, String request);
}
