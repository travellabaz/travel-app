package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.client.TelegramBotClient;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.SalesChangeLogEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.dao.repository.SalesChangeLogRepository;
import az.travellab.ms_travel_application.dao.repository.SalesRepository;
import az.travellab.ms_travel_application.exception.NotFoundException;
import az.travellab.ms_travel_application.model.enums.FilterType;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import az.travellab.ms_travel_application.model.response.SalesChangeLogResponse;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static az.travellab.ms_travel_application.exception.ExceptionMessages.*;
import static az.travellab.ms_travel_application.factory.PageableCommonMapper.PAGEABLE_COMMON_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesChangeLogMapper.SALES_CHANGE_LOG_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesMapper.SALES_MAPPER;
import static az.travellab.ms_travel_application.factory.TelegramBotMapper.TELEGRAM_BOT_MAPPER;
import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeNameByPhone;
import static az.travellab.ms_travel_application.model.enums.SalesMessageQueries.GET_ALL_SALES;
import static az.travellab.ms_travel_application.model.enums.SalesMessageQueries.GET_ALL_SALES_INFO_COUNT;
import static az.travellab.ms_travel_application.model.enums.SalesStatus.PENDING_FOR_APPROVE;
import static az.travellab.ms_travel_application.model.enums.TelegramNotificationType.SALE_UPDATE_NOTIFICATION;
import static az.travellab.ms_travel_application.util.HttpContextUtil.HTTP_CONTEXT_UTIL;
import static az.travellab.ms_travel_application.util.MapperUtil.MAPPER_UTIL;
import static az.travellab.ms_travel_application.util.PageUtil.PAGE_UTIL;
import static az.travellab.ms_travel_application.util.QueryGeneratorUtil.buildBaseQuery;
import static az.travellab.ms_travel_application.util.SalesSearchResponseTransformerUtil.SALES_SEARCH_RESPONSE_TRANSFORMER_UTIL;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.springframework.data.domain.Pageable.unpaged;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesService {
    private final ClientService clientService;
    private final CityService cityService;
    private final SalesCalculationService calcService;
    private final TelegramBotClient telegramBotClient;
    private final SalesRepository salesRepository;
    private final SalesChangeLogRepository salesChangeLogRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public String create(SalesRequest salesRequest) {
        var client = findClientByPhone(salesRequest.getClientNumber());
        var cities = cityService.getCitiesEntity(salesRequest.getCitiesIds());

        var salesEntity = SALES_MAPPER.generateSalesEntity(client, cities, salesRequest);
        salesRepository.save(salesEntity);

        return salesEntity.getNumber();
    }

    public void update(String user, SalesRequest salesRequest) {
        var salesEntity = getSalesEntityByNumber(salesRequest.getNumber());

        var changeLogEntities = salesEntity.getChangelogs();
        var newVersionId = getNextVersionId(changeLogEntities);
        changeLogEntities.add(
                SALES_CHANGE_LOG_MAPPER.generateSalesChangeLogEntity(
                        getEmployeeNameByPhone(user), salesEntity, newVersionId, salesRequest)
        );

        salesEntity.setStatus(PENDING_FOR_APPROVE);
        salesRepository.save(salesEntity);

        sendMessageAsync(salesEntity.getNumber(), salesEntity.getNote());
    }

    public SalesInfoResponse getInfo(String salesNumber) {
        var salesEntity = getSalesEntityByNumber(salesNumber);
        return SALES_MAPPER.generateSalesInfoResponse(salesEntity);
    }

    public void deleteChangeLog(String salesNumber, Integer versionId) {
        salesRepository.deleteChangeLog(salesNumber, versionId);
    }

    public void updateChangeLog(Integer versionId, SalesRequest request) {
        var changeLogEntity = salesChangeLogRepository.getChangeLogEntity(request.getNumber(), versionId)
                .orElseThrow(() -> new NotFoundException(CHANGELOG_NOT_FOUND.getMessage().formatted(versionId)));

        changeLogEntity.setRequest(MAPPER_UTIL.map(request));
        salesChangeLogRepository.save(changeLogEntity);
    }

    public List<SalesChangeLogResponse> getChangeLogs(String salesNumber) {
        var salesEntity = getSalesEntityByNumber(salesNumber);
        return SALES_CHANGE_LOG_MAPPER.generateSalesChangeLogResponse(salesEntity);
    }

    public void confirm(String salesNumber, Integer versionId) {
        var salesEntity = getSalesEntityByNumber(salesNumber);

        var changeLogEntity = getChangeLogEntityByVersion(salesEntity, versionId);

        var salesRequest = MAPPER_UTIL.map(changeLogEntity.getRequest(), SalesRequest.class);

        var client = findClientByPhone(salesRequest.getClientNumber());
        var cities = cityService.getCitiesEntity(salesRequest.getCitiesIds());

        //todo may be optimized for unused mapper calling and saving

        SALES_MAPPER.updateSalesEntity(versionId, client, cities, salesEntity, salesRequest);

        salesRepository.save(salesEntity);

        doCalculation(salesNumber, salesEntity);
    }

    public CommonPageableResponse<SalesInfoResponse> filter() {
        var nameValueParams = HTTP_CONTEXT_UTIL.getNameValueParams();
        var pageRequest = PAGE_UTIL.getPageRequest();

        var salesResponseList = supplyAsync(() -> getSalesList(nameValueParams, pageRequest));
        var salesResponseListCount = supplyAsync(() -> getSalesListCount(nameValueParams));

        var newSalesResponseList = salesResponseList.join().stream().map(SALES_MAPPER::generateSalesInfoResponse).toList();

        return PAGEABLE_COMMON_MAPPER.buildPageableClientResponse(newSalesResponseList, salesResponseListCount.join());
    }

    private void sendMessageAsync(String salesNumber, String note) {
        telegramBotClient.sendMessage(
                TELEGRAM_BOT_MAPPER.generateRequest(
                        SALE_UPDATE_NOTIFICATION, salesNumber, note
                )
        );
    }

    private ClientEntity findClientByPhone(String clientNumber) {
        return clientService.prepareFindClientEntityByPhoneFrom(clientNumber)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND.getMessage().formatted(clientNumber)));
    }

    private int getNextVersionId(List<SalesChangeLogEntity> changeLogEntities) {
        return changeLogEntities.isEmpty() ? 1 : changeLogEntities.get(changeLogEntities.size() - 1).getVersionId() + 1;
    }

    private SalesChangeLogEntity getChangeLogEntityByVersion(SalesEntity salesEntity, int versionId) {
        return salesEntity.getChangelogs().stream()
                .filter(log -> log.getVersionId() == versionId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Change log with version " + versionId + " not found"));
    }

    private void doCalculation(String salesNumber, SalesEntity salesEntity) {
        calcService.calculate(salesNumber, Optional.of(salesEntity));
    }

    private SalesEntity getSalesEntityByNumber(String salesNumber) {
        return salesRepository.findByNumber(salesNumber)
                .orElseThrow(() -> new NotFoundException(SALES_NOT_FOUND.getMessage().formatted(salesNumber)));
    }

    private List<SalesSearchResponse> getSalesList(Map<String, String> nameValueParams, Pageable pageable) {
        return buildBaseQuery(GET_ALL_SALES.getBaseQuery())
                .generateFilter(nameValueParams.keySet(), FilterType.SALES)
                .getTypedQuery(entityManager, nameValueParams, pageable)
                .unwrap(Query.class)
                .setTupleTransformer(SALES_SEARCH_RESPONSE_TRANSFORMER_UTIL)
                .getResultList();
    }

    private Long getSalesListCount(Map<String, String> nameValueParams) {
        return (Long) buildBaseQuery(GET_ALL_SALES_INFO_COUNT.getBaseQuery())
                .generateFilter(nameValueParams.keySet(), FilterType.SALES)
                .getTypedQuery(entityManager, nameValueParams, unpaged())
                .getSingleResult();
    }
}
