package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.dao.entity.SalesPaymentsEntity;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import org.springframework.beans.BeanUtils;

import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;

public enum SalesPaymentsMapper {
    SALES_PAYMENTS_MAPPER;

    public SalesPaymentsEntity generateSalesPaymentsEntity(SalesEntity salesEntity, SalesPaymentsDto paymentsRequest) {
        return SalesPaymentsEntity.builder()
                .sales(salesEntity)
                .amount(paymentsRequest.getAmount())
                .remaining(paymentsRequest.getRemaining())
                .type(paymentsRequest.getType())
                .change(paymentsRequest.getChange())
                .createdAt(DATE_UTIL.sync(paymentsRequest.getCreatedAt()))
                .updatedAt(DATE_UTIL.sync(DATE_UTIL.now()))
                .build();
    }

    public SalesPaymentsDto generatePaymentsDto(SalesPaymentsEntity paymentsEntity) {
        var paymentsDto = new SalesPaymentsDto();
        BeanUtils.copyProperties(paymentsEntity, paymentsDto);
        return paymentsDto;
    }
}
