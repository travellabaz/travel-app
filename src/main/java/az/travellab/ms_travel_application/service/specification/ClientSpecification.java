package az.travellab.ms_travel_application.service.specification;

import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.model.dto.ClientCriteriaDto;
import az.travellab.ms_travel_application.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


@AllArgsConstructor
public class ClientSpecification implements Specification<ClientEntity> {

    private ClientCriteriaDto clientCriteriaDto;

    @Override
    public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder()
                .addNullSafety(clientCriteriaDto.getId(), id -> cb.equal(root.get("id"), id))
                .addNullSafety(clientCriteriaDto.getNameFrom(), nameFrom -> cb.equal(root.get("nameFrom"), nameFrom))
                .addNullSafety(clientCriteriaDto.getNameTo(), nameTo -> cb.equal(root.get("nameTo"), nameTo))
                .addNullSafety(clientCriteriaDto.getPhoneFrom(), phoneFrom -> cb.equal(root.get("phoneFrom"), phoneFrom))
                .addNullSafety(clientCriteriaDto.getPhoneTo(), phoneTo -> cb.equal(root.get("phoneTo"), phoneTo))
                .addNullSafety(clientCriteriaDto.getMessage(), message -> cb.equal(root.get("message"), message))
                .addNullSafety(clientCriteriaDto.getPin(), pin -> cb.equal(root.get("pin"), pin))
                .addNullSafety(clientCriteriaDto.getMail(), mail -> cb.equal(root.get("mail"), mail))
                .addNullSafety(clientCriteriaDto.getCitizenCountry(), citizenCountry -> cb.equal(root.get("citizenCountry"), citizenCountry))
                .addNullSafety(clientCriteriaDto.getBirthDate(), birthDate -> cb.equal(root.get("birthDate"), birthDate))
                .addNullSafety(clientCriteriaDto.getCreatedAt(), createdAt -> cb.equal(root.get("createdAt"), createdAt))
                .addNullSafety(clientCriteriaDto.getUsername(), username -> cb.equal(root.get("username"), username))
                .build();
        return cb.and(predicates);
    }
}