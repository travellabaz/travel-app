package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesChangeLogEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.SalesChangeLogResponse;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;

import java.util.List;

import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeNameByPhone;
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
                .note(salesRequest.getNote())
                .createdAt(DATE_UTIL.sync(DATE_UTIL.now()))
                .build();
    }

    public List<SalesChangeLogResponse> generateSalesChangeLogResponse(SalesEntity salesEntity) {
        return salesEntity.getChangelogs().stream().map(
                log -> (
                        SalesChangeLogResponse.builder()
                                .username(log.getUsername())
                                .version(log.getVersionId())
                                .isApproved(log.getIsApproved())
                                .request(generateSalesInfoResponse(log))
                                .note(log.getNote())
                                .date(log.getCreatedAt())
                                .build()
                )).sorted((o1, o2) -> o2.getVersion() - o1.getVersion()).toList();
    }

    private SalesInfoResponse generateSalesInfoResponse(SalesChangeLogEntity entity) {
        var salesRequest = MAPPER_UTIL.map(entity.getRequest(), SalesRequest.class);

        return SalesInfoResponse.builder()
                .number(salesRequest.getNumber())
                .isOfficial(salesRequest.getIsOfficial())
                .type(salesRequest.getType())
                .clientClass(salesRequest.getClientClass())
                .salesperson(getEmployeeNameByPhone(salesRequest.getSalespersonNumber()))
                .hasClientRelationship(salesRequest.getHasClientRelationship())
                .tripStartDate(salesRequest.getTripStartDate())
                .tripEndDate(salesRequest.getTripEndDate())
                .isEmployeeBonusPaid(salesRequest.getIsEmployeeBonusPaid())
                .cancelReason(salesRequest.getCancelReason())
                .note(salesRequest.getNote())
                .createdAt(salesRequest.getCreatedAt())
                .components(salesRequest.getComponents())
                .payments(salesRequest.getPayments())
                .cities(salesRequest.getCitiesIds())
                .build();
    }
}
