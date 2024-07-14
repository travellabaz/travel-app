package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesChangeLogEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.SalesChangeLogResponse;

import java.util.List;

import static az.travellab.ms_travel_application.util.MapperUtil.MAPPER_UTIL;

public enum SalesChangeLogMapper {
    SALES_CHANGE_LOG_MAPPER;

    public SalesChangeLogEntity generateSalesChangeLogEntity(Employee user, SalesEntity salesEntity, int versionId, SalesRequest salesRequest) {
        return SalesChangeLogEntity.builder()
                .sales(salesEntity)
                .username(user.getName())
                .versionId(versionId)
                .request(MAPPER_UTIL.map(salesRequest))
                .isApproved(false)
                .build();
    }

    public List<SalesChangeLogResponse> generateSalesChangeLogResponse(List<SalesChangeLogEntity> changeLogEntities) {
        return changeLogEntities.stream().map(
                log -> (
                        SalesChangeLogResponse.builder()
                                .username(log.getUsername())
                                .version(log.getVersionId())
                                .request(MAPPER_UTIL.map(log.getRequest(), SalesRequest.class))
                                .isApproved(log.getIsApproved())
                                .date(log.getCreatedAt())
                                .build()
                )).sorted((o1, o2) -> o2.getVersion() - o1.getVersion()).toList();
    }
}
