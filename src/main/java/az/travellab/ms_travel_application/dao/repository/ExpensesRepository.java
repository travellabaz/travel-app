package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends CrudRepository<ExpensesEntity, Long> {

    @Query(value = """
            SELECT *
            FROM expenses
            WHERE DATE_TRUNC('month', created_at) = DATE_TRUNC('month', CURRENT_DATE)
            ORDER BY id DESC
            """, nativeQuery = true)
    List<ExpensesEntity> getCurrentMonthExpenses();
}
