package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.SalesPaymentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPaymentsRepository extends CrudRepository<SalesPaymentsEntity, Long> {
}
