package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.dao.entity.SalesPaymentsEntity;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import az.travellab.ms_travel_application.model.request.sales.SalesPaymentsRequest;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public enum SalesPaymentsMapper {
    SALES_PAYMENTS_MAPPER;

    public SalesPaymentsEntity generateSalesPaymentsEntity(SalesEntity salesEntity, SalesPaymentsRequest paymentsRequest) {

        var date = paymentsRequest.getDate() == null ? LocalDateTime.now() : paymentsRequest.getDate();

        return SalesPaymentsEntity.builder()
                .sales(salesEntity)
                .amount(paymentsRequest.getAmount())
                .remaining(paymentsRequest.getRemaining())
                .type(paymentsRequest.getType())
                .change(paymentsRequest.getChange())
                .createdAt(date)
                .build();
    }

    public void updateSalesPaymentsEntity(SalesPaymentsEntity paymentsEntity, SalesPaymentsRequest paymentsRequest) {
        BeanUtils.copyProperties(paymentsRequest, paymentsEntity);
        paymentsEntity.setCreatedAt(paymentsRequest.getDate());
    }

    public SalesPaymentsDto generatePaymentsDto(SalesPaymentsEntity paymentsEntity) {
        var paymentsDto = new SalesPaymentsDto();
        BeanUtils.copyProperties(paymentsEntity, paymentsDto);
        return paymentsDto;
    }
}
