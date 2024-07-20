package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesComponentsEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;

public enum SalesComponentsMapper {
    SALES_COMPONENTS_MAPPER;

    public SalesComponentsEntity generateSalesComponentsEntity(SalesEntity salesEntity, SalesComponentsDto componentsRequest) {

        var date = componentsRequest.getCreatedAt() == null ? LocalDateTime.now() : componentsRequest.getCreatedAt();

        return SalesComponentsEntity.builder()
                .sales(salesEntity)
                .type(componentsRequest.getType())
                .name(componentsRequest.getName())
                .bookingNumber(componentsRequest.getBookingNumber())
                .percentage(componentsRequest.getPercentage())
                .description(componentsRequest.getDescription())
                .purchasedAmount(componentsRequest.getPurchasedAmount())
                .soldAmount(componentsRequest.getSoldAmount())
                .paidAmount(componentsRequest.getPaidAmount())
                .remainedAmount(componentsRequest.getRemainedAmount())
                .transferCommission(componentsRequest.getTransferCommission())
                .createdAt(DATE_UTIL.sync(date))
                .status(componentsRequest.getStatus())
                .build();
    }

    public SalesComponentsDto generateComponentsDto(SalesComponentsEntity componentsEntity) {
        var componentDto = new SalesComponentsDto();
        BeanUtils.copyProperties(componentsEntity, componentDto);
        return componentDto;
    }
}
