package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SalesRepository extends CrudRepository<SalesEntity, Long> {

//    @EntityGraph(value = "Sales.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<SalesEntity> findByNumber(@Param("saleNumber") String saleNumber);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE
            FROM sales_change_log scl
            WHERE scl.id = (SELECT scl.id
                            FROM sales sl
                                     JOIN sales_change_log scl ON sl.id = scl.sales_id
                            WHERE sl.number = :salesNumber
                              AND scl.version_id = :versionId)
            """, nativeQuery = true)
    void deleteChangeLog(@Param("salesNumber") String salesNumber, @Param("versionId") Integer versionId);

    @Query(value = """
            SELECT *
            FROM sales sales_table
                     LEFT JOIN sales_change_log sales_change_log_table
                               ON sales_change_log_table.sales_id = sales_table.id
            WHERE sales_table.number = :number
            ORDER BY sales_change_log_table.version_id;
            """, nativeQuery = true)
    SalesEntity getLastUpdatedVersion(@Param("number") String salesNumber);
}
