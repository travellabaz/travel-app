package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
