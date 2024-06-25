package az.travellab.ms_travel_application.dao.repository;

import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>, JpaSpecificationExecutor<ClientEntity> {
    Optional<ClientEntity> findClientEntityByPhoneFrom(String phone);

    @Query("SELECT c FROM ClientEntity c WHERE EXTRACT(MONTH FROM c.birthDate) = :month AND EXTRACT(DAY FROM c.birthDate) = :day")
    List<ClientEntity> findClientEntityByBirthMonthAndDay(int month, int day);
}
