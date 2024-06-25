package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.dao.projection.EmployeesBonusProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesRepository extends CrudRepository<SalesEntity, Long> {

    Optional<SalesEntity> findByNumber(@Param("saleNumber") String saleNumber);

    void deleteByNumber(@Param("saleNumber") String saleNumber);

    @Query(value = """
            SELECT salesperson, SUM(employee_bonus) AS bonus FROM sales
            WHERE is_employee_bonus_paid IS FALSE
            AND status = 'COMPLETED'
            AND (:fromDate IS NULL OR :toDate IS NULL OR created_at BETWEEN :fromDate AND :toDate)
            GROUP BY salesperson
            """, nativeQuery = true
    )
    List<EmployeesBonusProjection> getEmployeesBonus(String fromDate, String toDate);
}
