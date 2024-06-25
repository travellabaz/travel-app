package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.SalesComponentsEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.dao.entity.SalesPaymentsEntity;
import az.travellab.ms_travel_application.dao.repository.SalesComponentsRepository;
import az.travellab.ms_travel_application.dao.repository.SalesPaymentsRepository;
import az.travellab.ms_travel_application.dao.repository.SalesRepository;
import az.travellab.ms_travel_application.exception.NotFoundException;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import az.travellab.ms_travel_application.model.enums.FilterType;
import az.travellab.ms_travel_application.model.request.sales.SalesComponentsRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesPaymentsRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesUpdateRequest;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static az.travellab.ms_travel_application.exception.ExceptionMessages.CLIENT_NOT_FOUND;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.ENTITY_NOT_FOUND;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.SALES_ALREADY_EXISTS;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.SALES_NOT_FOUND;
import static az.travellab.ms_travel_application.factory.PageableCommonMapper.PAGEABLE_COMMON_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesComponentsMapper.SALES_COMPONENTS_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesMapper.SALES_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesPaymentsMapper.SALES_PAYMENTS_MAPPER;
import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeNameByPhone;
import static az.travellab.ms_travel_application.model.enums.SalesMessageQueries.GET_ALL_SALES;
import static az.travellab.ms_travel_application.model.enums.SalesMessageQueries.GET_ALL_SALES_COUNT;
import static az.travellab.ms_travel_application.util.HttpContextUtil.HTTP_CONTEXT_UTIL;
import static az.travellab.ms_travel_application.util.PageUtil.PAGE_UTIL;
import static az.travellab.ms_travel_application.util.QueryGeneratorUtil.buildBaseQuery;
import static az.travellab.ms_travel_application.util.SalesResponseTransformerUtil.SALES_RESPONSE_TRANSFORMER;
import static az.travellab.ms_travel_application.util.SecureRandomUtil.getRandomNumbers;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.springframework.data.domain.Pageable.unpaged;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesService {
    private final ClientService clientService;
    private final CityService cityService;
    private final SalesCalculationService calcService;
    private final SalesRepository salesRepository;
    private final SalesComponentsRepository salesComponentsRepository;
    private final SalesPaymentsRepository salesPaymentsRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public List<SalesComponentsDto> getSalesComponents(String salesNumber) {
        var salesComponents = getSalesEntityByNumber(salesNumber);

        return salesComponents.getComponents().stream().map(SALES_COMPONENTS_MAPPER::generateComponentsDto).toList();
    }

    public List<SalesPaymentsDto> getSalesPayments(String salesNumber) {
        var salesComponents = getSalesEntityByNumber(salesNumber);

        return salesComponents.getPayments().stream().map(SALES_PAYMENTS_MAPPER::generatePaymentsDto).toList();
    }

    public void createSales(SalesRequest salesRequest) {

        var salesNumber = generateUniqueSalesNumber();
        validateSalesNumber(salesNumber);

        var salesperson = getEmployeeNameByPhone(salesRequest.getSalespersonNumber());

        var client = clientService.prepareFindClientEntityByPhoneFrom(salesRequest.getClientNumber()).orElseThrow(
                () -> new NotFoundException(CLIENT_NOT_FOUND.getMessage().formatted(salesRequest.getClientNumber())));

        var cities = cityService.getCitiesEntity(salesRequest.getCitiesIds());

        var salesEntity = SALES_MAPPER.generateSalesEntity(salesNumber, salesperson, client, cities, salesRequest);

        salesRepository.save(salesEntity);

        //doCalculation(salesNumber, salesEntity); //TODO calc method throw UnsupportedOperationException exception
    }

    public void updateSales(String salesNumber, SalesUpdateRequest request) {

        var salesEntity = getSalesEntityByNumber(salesNumber);

        var salesperson = getEmployeeNameByPhone(request.getSalespersonNumber());

        var client = clientService.prepareFindClientEntityByPhoneFrom(request.getClientNumber()).orElseThrow(
                () -> new NotFoundException(CLIENT_NOT_FOUND.getMessage().formatted(request.getClientNumber())));

        var cities = cityService.getCitiesEntity(request.getCitiesIds());

        SALES_MAPPER.updateSalesEntity(salesEntity, salesperson, client, cities, request);

        salesRepository.save(salesEntity);

        doCalculation(salesNumber, salesEntity);
    }

    @Transactional
    public void deleteSales(String salesNumber) {
        salesRepository.deleteByNumber(salesNumber);
    }

    public SalesInfoResponse getSalesInfo(String salesNumber) {
        var salesEntity = getSalesEntityByNumber(salesNumber);

        var clientEntity = salesEntity.getClient();
        var cityEntities = salesEntity.getCities();

        return SALES_MAPPER.generateSalesInfoResponse(salesEntity, clientEntity, cityEntities);
    }

    @Transactional
    public void updateComponents(String salesNumber, List<SalesComponentsRequest> componentsRequest) {
        updateEntities(
                salesNumber,
                componentsRequest,
                SalesEntity::getComponents,
                SALES_COMPONENTS_MAPPER::generateSalesComponentsEntity,
                SALES_COMPONENTS_MAPPER::updateSalesComponentsEntity,
                salesComponentsRepository::deleteById,
                SalesComponentsRequest::getId,
                SalesComponentsEntity::getId
        );
    }

    @Transactional
    public void updatePayments(String salesNumber, List<SalesPaymentsRequest> paymentsRequest) {
        updateEntities(
                salesNumber,
                paymentsRequest,
                SalesEntity::getPayments,
                SALES_PAYMENTS_MAPPER::generateSalesPaymentsEntity,
                SALES_PAYMENTS_MAPPER::updateSalesPaymentsEntity,
                salesPaymentsRepository::deleteById,
                SalesPaymentsRequest::getId,
                SalesPaymentsEntity::getId
        );
    }

    public CommonPageableResponse<SalesSearchResponse> getSales() {
        var nameValueParams = HTTP_CONTEXT_UTIL.getNameValueParams();
        var pageRequest = PAGE_UTIL.getPageRequest();

        var salesResponseList = supplyAsync(() -> getSalesList(nameValueParams, pageRequest));
        var salesResponseListCount = supplyAsync(() -> getSalesListCount(nameValueParams));
        return PAGEABLE_COMMON_MAPPER.buildPageableClientResponse(salesResponseList.join(), salesResponseListCount.join());
    }

    private void doCalculation(String salesNumber, SalesEntity salesEntity) {
        calcService.calculate(salesNumber, Optional.of(salesEntity));
    }

    private <E, R> void updateEntities(
            String salesNumber,
            List<R> requestList,
            Function<SalesEntity, List<E>> getEntityList,
            BiFunction<SalesEntity, R, E> createNewEntity,
            BiConsumer<E, R> updateExistingEntity,
            Consumer<Long> deleteEntityById,
            Function<R, Long> getRequestId,
            Function<E, Long> getEntityId
    ) {
        var salesEntity = getSalesEntityByNumber(salesNumber);
        var entityList = getEntityList.apply(salesEntity);

        // Create a map for quick lookup of existing entities by ID
        Map<Long, E> entityMap = entityList.stream()
                .collect(Collectors.toMap(getEntityId, entity -> entity));

        // Track entities to be added
        var entitiesToAdd = new ArrayList<E>();

        // Track IDs of entities to be removed
        var entityIds = new HashSet<>(entityMap.keySet());

        for (var request : requestList) {
            Long id = getRequestId.apply(request);
            if (id == null) {
                // Create new entity
                var newEntity = createNewEntity.apply(salesEntity, request);
                entitiesToAdd.add(newEntity);
            } else {
                // Update existing entity
                var existingEntity = entityMap.get(id);
                if (existingEntity != null) {
                    updateExistingEntity.accept(existingEntity, request);
                    entityIds.remove(id);
                } else {
                    throw new NotFoundException(ENTITY_NOT_FOUND.getMessage().formatted(id));
                }
            }
        }

        // Remove entities no longer present in the request
        entityIds.forEach(deleteEntityById);
        entityList.removeIf(entity -> entityIds.contains(getEntityId.apply(entity)));

        // Add new entities to the entity's list
        entityList.addAll(entitiesToAdd);
        salesRepository.save(salesEntity);

        doCalculation(salesNumber, salesEntity);
    }

    private SalesEntity getSalesEntityByNumber(String salesNumber) {
        return salesRepository.findByNumber(salesNumber)
                .orElseThrow(() -> new NotFoundException(SALES_NOT_FOUND.getMessage().formatted(salesNumber)));
    }

    private String generateUniqueSalesNumber() {
        final var GENERATION_PREFIX = "TL-";
        return GENERATION_PREFIX.concat(getRandomNumbers());
    }

    private void validateSalesNumber(String salesNumber) {
        if (salesRepository.findByNumber(salesNumber).isPresent()) {
            throw new IllegalArgumentException(SALES_ALREADY_EXISTS.getMessage().formatted(salesNumber));
        }
    }

    private List<SalesSearchResponse> getSalesList(Map<String, String> nameValueParams, Pageable pageable) {
        return buildBaseQuery(GET_ALL_SALES.getBaseQuery())
                .generateFilter(nameValueParams.keySet(), FilterType.SALES)
                .endWith(GET_ALL_SALES.getEndOfQuery())
                .getTypedQuery(entityManager, nameValueParams, pageable)
                .unwrap(Query.class)
                .setTupleTransformer(SALES_RESPONSE_TRANSFORMER)
                .getResultList();
    }

    private Long getSalesListCount(Map<String, String> nameValueParams) {
        return (Long) buildBaseQuery(GET_ALL_SALES_COUNT.getBaseQuery())
                .generateFilter(nameValueParams.keySet(), FilterType.SALES)
                .getTypedQuery(entityManager, nameValueParams, unpaged())
                .getSingleResult();
    }
}
