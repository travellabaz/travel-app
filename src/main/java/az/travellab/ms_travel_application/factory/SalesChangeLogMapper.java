package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.SalesChangeLogEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.SalesChangeLogResponse;

import java.time.LocalDateTime;
import java.util.List;

import static az.travellab.ms_travel_application.factory.SalesMapper.SALES_MAPPER;
import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;
import static az.travellab.ms_travel_application.util.MapperUtil.MAPPER_UTIL;

public enum SalesChangeLogMapper {
    SALES_CHANGE_LOG_MAPPER;

    public SalesChangeLogEntity generateSalesChangeLogEntity(String username, SalesEntity salesEntity, int versionId, SalesRequest salesRequest) {
        salesRequest.setNumber(salesEntity.getNumber());

        return SalesChangeLogEntity.builder()
                .sales(salesEntity)
                .username(username)
                .versionId(versionId)
                .request(MAPPER_UTIL.map(salesRequest))
                .isApproved(false)
                .createdAt(DATE_UTIL.sync(LocalDateTime.now()))
                .build();
    }

    public List<SalesChangeLogResponse> generateSalesChangeLogResponse(List<SalesChangeLogEntity> changeLogEntities, ClientEntity client, List<CityEntity> cities) {
        return changeLogEntities.stream().map(
                log -> (
                        SalesChangeLogResponse.builder()
                                .username(log.getUsername())
                                .version(log.getVersionId())
                                .request(SALES_MAPPER.generateSalesInfoResponse(log.getRequest(), client, cities))
                                .isApproved(log.getIsApproved())
                                .date(DATE_UTIL.sync(log.getCreatedAt()))
                                .build()
                )).sorted((o1, o2) -> o2.getVersion() - o1.getVersion()).toList();
    }
}
