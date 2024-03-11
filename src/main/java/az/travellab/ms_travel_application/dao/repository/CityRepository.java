package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.CityEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    @EntityGraph(attributePaths = "country")
    Optional<List<CityEntity>> findByIdIn(List<Long> ids);

}
